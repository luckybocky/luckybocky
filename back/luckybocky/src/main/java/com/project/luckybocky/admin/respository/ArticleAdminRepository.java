package com.project.luckybocky.admin.respository;

import org.springframework.data.domain.Pageable;

import com.project.luckybocky.admin.dto.ArticleAdminListResDto;

public interface ArticleAdminRepository {
	ArticleAdminListResDto findArticlesWithFilters(String startDate, String endDate, Boolean deleted,
		Pageable pageable);
}
