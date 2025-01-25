package com.project.luckybocky.article.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WriteArticleDto {
	@Schema(description = "게시글을 달 복주머니 번호")
	private int pocketSeq;
	//    private String userKey;    // null 이라면 비회원
	@Schema(description = "게시글 작성자 닉네임")
	private String nickname;
	@Schema(description = "게시글 내용")
	private String content;
	@Schema(description = "복 번호")   // ** 이거 fortuneImg가 번호로 바뀌면서, fortuneImg를 주는 거랑 다른게 없어졌음
	private int fortuneSeq;

	@Schema(description = "푸시 클릭 시 이동할 url")
	private String url;
}
