package com.project.luckybocky.article.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WriteArticleDto {
    private int pocketSeq;
//    private String userKey;    // null 이라면 비회원
    private String nickname;
    private String content;
    private int fortuneSeq;
    private boolean visibility;
}
