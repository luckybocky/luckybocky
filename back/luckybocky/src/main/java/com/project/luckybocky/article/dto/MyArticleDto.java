package com.project.luckybocky.article.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class MyArticleDto {
	private String pocketOwner;  // 복주머니 주인
	private String pocketAddress; //복주머니 주소
	private String articleOwner; // 복 작성자
	private String content; // 복주머니 주인에게 말한 내용
	private String fortuneName; // 복의 종류
	private int fortuneImg; // 복의 이미지
	private String createdAt; // 복을 넣은 날짜
}
