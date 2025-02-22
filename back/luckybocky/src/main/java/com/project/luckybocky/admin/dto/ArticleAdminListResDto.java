package com.project.luckybocky.admin.dto;

import org.springframework.data.domain.Page;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ArticleAdminListResDto {
	private Page<ArticleAdminDto> articleBackOfficeResDto;

	public static ArticleAdminListResDto toArticleBackOfficeResDto(
		Page<ArticleAdminDto> articleBackOfficeDtoList) {
		return ArticleAdminListResDto.builder()
			.articleBackOfficeResDto(articleBackOfficeDtoList)
			.build();
	}
}
