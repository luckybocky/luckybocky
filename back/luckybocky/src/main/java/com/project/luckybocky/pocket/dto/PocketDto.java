package com.project.luckybocky.pocket.dto;

import java.util.List;

import com.project.luckybocky.article.dto.ArticleSummaryDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PocketDto {
	@Schema(description = "복주머니 번호")
	private int pocketSeq;
	@Schema(description = "복주머니 주인 닉네임")
	private String userNickname;
	@Schema(description = "복주머니 공개여부")
	private boolean fortuneVisibility;
	@Schema(description = "복주머니에 달린 복")
	private List<ArticleSummaryDto> articles;
}
