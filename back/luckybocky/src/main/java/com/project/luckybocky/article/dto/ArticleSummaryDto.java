package com.project.luckybocky.article.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ArticleSummaryDto {
	@Schema(description = "게시글 번호")
	private int articleSeq;
	@Schema(description = "복 이름")
	private String fortuneName;
	@Schema(description = "복 번호")
	private int fortuneImg;
	@Schema(description = "게시글 작성자 닉네임")
	private String userNickname;
}
