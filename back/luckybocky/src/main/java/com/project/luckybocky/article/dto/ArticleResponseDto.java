package com.project.luckybocky.article.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleResponseDto {
	@Schema(description = "게시글 공개여부")
	private boolean articleVisibility;
	@Schema(description = "게시글 번호")
	private int articleSeq;

	//12-31 창희 추가
	@Schema(description = "게시글 작성자 식별키")
	private String userKey;
	@Schema(description = "게시글 작성자 닉네임")
	private String userNickname;

	@Schema(description = "게시글 내용")
	private String articleContent;
	@Schema(description = "복 이름")
	private String fortuneName;
	@Schema(description = "복 번호")
	private int fortuneImg;
	@Schema(description = "게시글 작성일시")
	private String createdAt;

	// public ArticleResponseDto(Article article) {
	//     this.articleVisibility = article.isArticleVisibility();
	//     this.articleSeq = article.getArticleSeq();
	//
	//     //12-31 창희 추가
	//     this.userKey = (article.getUser() == null) ? null : article.getUser().getUserKey();  // guest일 때 예외처리 추가
	//     this.userNickname = article.getUserNickname();
	//     this.articleContent = article.getArticleContent();
	//     this.articleComment = article.getArticleComment();
	//
	//     this.fortuneName = article.getFortune().getFortuneName();  // getFortune -> getFortunes
	//     this.fortuneImg = article.getFortune().getFortuneSeq();
	//     this.createdAt = article.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	// }

}
