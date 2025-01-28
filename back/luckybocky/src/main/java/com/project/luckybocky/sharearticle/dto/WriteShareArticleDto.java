package com.project.luckybocky.sharearticle.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class WriteShareArticleDto {
	@Schema(description = "공유 게시글 내용")
	private String content;

	@Schema(description = "복 번호")   // ** 이거 fortuneImg가 번호로 바뀌면서, fortuneImg를 주는 거랑 다른게 없어졌음
	private int fortuneSeq;
}
