package com.project.luckybocky.article.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CommentDto {
	@Schema(description = "게시글 번호")
	private int articleSeq;
	@Schema(description = "게시글 답글 내용")
	private String comment;

	@Schema(description = "푸시 클릭 시 이동할 url")
	private String url;
}
