package com.project.luckybocky.admin.respository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.project.luckybocky.admin.dto.ArticleAdminDto;
import com.project.luckybocky.admin.dto.ArticleAdminListResDto;
import com.project.luckybocky.article.entity.QArticle;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ArticleAdminRepositoryImpl implements ArticleAdminRepository {
	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public ArticleAdminListResDto findArticlesWithFilters(String startDate, String endDate, Boolean deleted,
		Pageable pageable) {
		QArticle article = QArticle.article;

		OrderSpecifier<?> orderSpecifier = Objects.requireNonNull(pageable.getSort().
			getOrderFor("createdAt")).isAscending() ? article.createdAt.asc() : article.createdAt.desc();

		List<ArticleAdminDto> content = jpaQueryFactory
			.select(Projections.bean(ArticleAdminDto.class,
				article.articleSeq,
				article.articleContent,
				article.fortune.fortuneSeq,
				article.user.userSeq,
				article.userNickname,
				article.pocket.pocketSeq,
				article.shareArticle.shareArticleSeq,
				article.createdAt,
				article.modifiedAt,
				article.isDeleted))
			.from(article)
			.where(
				startDate != null ? article.createdAt.goe(LocalDate.parse(startDate).atStartOfDay()) : null,
				endDate != null ? article.createdAt.loe(LocalDate.parse(endDate).atTime(LocalTime.MAX)) : null,
				deleted != null ? deleted ? article.isDeleted.isTrue() : article.isDeleted.isFalse() : null
			)
			.orderBy(orderSpecifier)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		return ArticleAdminListResDto.toArticleBackOfficeResDto(new PageImpl<>(content, pageable, content.size()));
	}
}
