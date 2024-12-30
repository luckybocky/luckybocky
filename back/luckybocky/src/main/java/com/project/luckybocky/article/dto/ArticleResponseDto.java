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

    private String fortuneName;
    private String fortuneImgUrl;
    private String createdAt;

    private boolean guest;    // null일 경우 비회원
    private boolean visibility;     // 공개여부

    public ArticleResponseDto(Article article){
        this.articleSeq = article.getArticleSeq();
        this.nickname = article.getUserNickname();
        this.content = article.getArticleContent();
        this.comment = article.getArticleComment();
        this.fortuneName = article.getFortune().getFortuneName();  // getFortune -> getFortunes
        this.fortuneImgUrl = article.getFortune().getFortuneImg();
        this.createdAt = article.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.guest = (article.getUser() == null) ? true : false;
        this.visibility = article.getArticleVisibility() == 1 ? true : false;
    }
}
