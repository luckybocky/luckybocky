package com.project.luckybocky.sharearticle.dto;

import java.util.List;

import com.project.luckybocky.article.dto.ArticleResponseDto;

import io.swagger.v3.oas.annotations.media.Schema;
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
	@Schema(description = "공유 게시글 식별자")
	private Integer shareArticleSeq;

	@Schema(description = "공유게시글 주인, 유저의 식별자")
	private String userKey;

	@Schema(description = "유저의 닉네임")
	private String userNickname;

	@Schema(description = "복의 식별자")
	private int fortuneSeq;

	@Schema(description = "복의 이름")
	private String fortuneName;

	@Schema(description = "공유게시글을 저장한 복주머니 주소")
	private String pocketAddress;

	private List<ArticleResponseDto> articles;

	@Schema(description = "공유 게시글 내용")
	private String shareArticleContent;

	@Schema(description = "공유 게시글 주소")
	private String shareArticleAddress;

	@Schema(description = "다른 사람들이 게시글을 저장한 횟수")
	private Integer shareCount;

}
