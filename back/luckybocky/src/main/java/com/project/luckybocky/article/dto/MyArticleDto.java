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
public class MyArticleDto {
	@Schema(description = "복주머니 주인")
	private String pocketOwner;

	@Schema(description = "복주머니 주소")
	private String pocketAddress;

	@Schema(description = "복 작성자")
	private String articleOwner;

	@Schema(description = "복주머니 주인에게 말한 내용")
	private String content;

	@Schema(description = "복의 종류")
	private String fortuneName;

	@Schema(description = "복의 이미지")
	private int fortuneImg;

	@Schema(description = "복을 넣은 날짜")
	private String createdAt;
}
