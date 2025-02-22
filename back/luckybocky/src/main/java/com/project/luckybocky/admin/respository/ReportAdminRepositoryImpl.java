package com.project.luckybocky.admin.respository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.project.luckybocky.admin.dto.ReportAdminDto;
import com.project.luckybocky.admin.dto.ReportAdminListResDto;
import com.project.luckybocky.report.entity.QReport;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ReportAdminRepositoryImpl implements ReportAdminRepository {
	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public ReportAdminListResDto findReportsWithFilters(String startDate, String endDate, Pageable pageable) {
		QReport report = QReport.report;

		OrderSpecifier<?> orderSpecifier = Objects.requireNonNull(pageable.getSort().
			getOrderFor("createdAt")).isAscending() ? report.createdAt.asc() : report.createdAt.desc();

		List<ReportAdminDto> content = jpaQueryFactory
			.select(Projections.bean(ReportAdminDto.class,
				report.reportSeq,
				report.reportContent,
				report.reportType,
				report.article.articleSeq,
				ExpressionUtils.as(report.offender.userSeq, "offenderSeq"),
				ExpressionUtils.as(report.reporter.userSeq, "reporterSeq"),
				report.article.articleContent,
				report.createdAt,
				report.modifiedAt,
				report.isDeleted))
			.from(report)
			.where(
				startDate != null ? report.createdAt.goe(LocalDate.parse(startDate).atStartOfDay()) : null,
				endDate != null ? report.createdAt.loe(LocalDate.parse(endDate).atTime(LocalTime.MAX)) : null
			)
			.orderBy(orderSpecifier)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		return ReportAdminListResDto.toReportBackOfficeListResDto(
			new PageImpl<>(content, pageable, content.size()));
	}
}
