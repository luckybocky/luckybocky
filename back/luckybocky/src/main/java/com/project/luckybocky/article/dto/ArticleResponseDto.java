package com.project.luckybocky.article.dto;

import com.project.luckybocky.article.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;

@Getter
@Setter
@AllArgsConstructor
public class ArticleResponseDto {
    private int articleSeq;

    private String nickname;
    private String content;
    private String comment;

    //12-31 창희 추가
    private String userKey;

    private String fortuneName;
    private String fortuneImg;
    private String createdAt;

    private boolean guest;    // null일 경우 비회원
    private boolean articleVisibility;     // article 공개여부

    public ArticleResponseDto(Article article){
        this.articleSeq = article.getArticleSeq();
        this.nickname = article.getUserNickname();
        this.content = article.getArticleContent();
        this.comment = article.getArticleComment();

        //12-31 창희 추가
        this.userKey = (article.getUser() == null) ? null : article.getUser().getUserKey();  // guest일 때 예외처리 추가

        this.fortuneName = article.getFortune().getFortuneName();  // getFortune -> getFortunes
        this.fortuneImg = article.getFortune().getFortuneImg();
        this.createdAt = article.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.guest = (article.getUser() == null) ? true : false;
        this.articleVisibility = article.getArticleVisibility() == 1 ? true : false;
    }
}
