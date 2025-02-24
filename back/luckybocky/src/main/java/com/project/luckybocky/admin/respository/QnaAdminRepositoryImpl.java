package com.project.luckybocky.admin.respository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.project.luckybocky.admin.dto.QnaAdminDto;
import com.project.luckybocky.admin.dto.QnaAdminListResDto;
import com.project.luckybocky.qna.entity.QQna;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class QnaAdminRepositoryImpl implements QnaAdminRepository {
	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public QnaAdminListResDto findQnaWithFilters(String startDate, String endDate, Boolean deleted, Boolean answer,
		Pageable pageable) {
		QQna qna = QQna.qna;

		OrderSpecifier<?> orderSpecifier = Objects.requireNonNull(pageable.getSort().
			getOrderFor("createdAt")).isAscending() ? qna.createdAt.asc() : qna.createdAt.desc();

		List<QnaAdminDto> content = jpaQueryFactory
			.select(Projections.bean(QnaAdminDto.class,
				qna.qnaSeq,
				qna.user.userSeq,
				qna.title,
				qna.content,
				qna.secretStatus,
				qna.answer,
				qna.createdAt,
				qna.modifiedAt,
				qna.isDeleted))
			.from(qna)
			.where(
				answer != null ? answer ? qna.answer.isNotNull() : qna.answer.isNull() : null,
				deleted != null ? deleted ? qna.isDeleted.isTrue() : qna.isDeleted.isFalse() : null,
				startDate != null ? qna.createdAt.goe(LocalDate.parse(startDate).atStartOfDay()) : null,
				endDate != null ? qna.createdAt.loe(LocalDate.parse(endDate).atTime(LocalTime.MAX)) : null
			)
			.orderBy(orderSpecifier)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		return QnaAdminListResDto.toQnaBackOfficeListResDto(new PageImpl<>(content, pageable, content.size()));
	}
}
