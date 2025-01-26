package com.project.luckybocky.sharearticle.dto;

import java.util.List;

import com.project.luckybocky.article.dto.ArticleResponseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShareArticleDto {
	private Integer shareArticleSeq;

	private String userKey;

	private String userNickname;

	private int fortuneSeq;

	private List<ArticleResponseDto> articles;

	private String shareArticleContent;

	private String shareArticleAddress;

	private Integer shareCount;

}
