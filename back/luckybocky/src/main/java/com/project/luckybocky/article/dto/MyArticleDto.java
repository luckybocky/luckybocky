package com.project.luckybocky.article.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MyArticleDto {
    private String articleOwner; // 복주머니 작성자
    private String content; // 복주머니 주인에게 말한 내용
    private String fortuneName; // 복의 종류
    private String fortuneImg; // 복의 이미지
    private String createdAt; // 복을 넣은 날짜
}
