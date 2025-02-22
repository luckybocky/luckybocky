package com.project.luckybocky.admin.respository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.project.luckybocky.admin.dto.FeedbackAdminDto;
import com.project.luckybocky.admin.dto.FeedbackAdminListResDto;
import com.project.luckybocky.feedback.entity.QFeedback;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class FeedbackAdminRepositoryImpl implements FeedbackAdminRepository {
	private final JPAQueryFactory jpaQueryFactory;
	private final QFeedback feedback = QFeedback.feedback;

	@Override
	public FeedbackAdminListResDto findFeedbacksWithFilters(String startDate, String endDate, Pageable pageable) {
		OrderSpecifier<?> orderSpecifier = Objects.requireNonNull(pageable.getSort().
			getOrderFor("createdAt")).isAscending() ? feedback.createdAt.asc() : feedback.createdAt.desc();

		List<FeedbackAdminDto> content = jpaQueryFactory
			.select(Projections.bean(FeedbackAdminDto.class,
				feedback.feedbackSeq,
				feedback.feedbackContent,
				feedback.feedbackRate,
				feedback.createdAt,
				feedback.modifiedAt,
				feedback.isDeleted))
			.from(feedback)
			.where(
				startDate != null ? feedback.createdAt.goe(LocalDate.parse(startDate).atStartOfDay()) : null,
				endDate != null ? feedback.createdAt.loe(LocalDate.parse(endDate).atTime(LocalTime.MAX)) : null
			)
			.orderBy(orderSpecifier)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		return FeedbackAdminListResDto.toFeedbackBackOfficeResDto(
			new PageImpl<>(content, pageable, content.size()), findFeedbackAverageRate());
	}

	@Override
	public double findFeedbackAverageRate() {
		return Objects.requireNonNullElse(
			jpaQueryFactory
				.select(feedback.feedbackRate.avg())
				.from(feedback)
				.fetchOne(),
			0.0);
	}
}
