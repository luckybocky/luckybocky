package com.project.luckybocky.article.dto;

import com.project.luckybocky.article.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;

@Getter
@Setter
@AllArgsConstructor
public class ArticleResponseDto {
    private boolean articleVisibility;  // article 공개여부
    private int articleSeq;

    //12-31 창희 추가
    private String userKey;             // 작성자 Key
    private String userNickname;        // 작성자 닉네임

    private String articleContent;
    private String articleComment;
    private String fortuneName;
    private String fortuneImg;
    private String createdAt;


    public ArticleResponseDto(Article article){
        this.articleVisibility = article.getArticleVisibility() == 1 ? true : false;
        this.articleSeq = article.getArticleSeq();

        //12-31 창희 추가
        this.userKey = (article.getUser() == null) ? null : article.getUser().getUserKey();  // guest일 때 예외처리 추가
        this.userNickname = article.getUserNickname();
        this.articleContent = article.getArticleContent();
        this.articleComment = article.getArticleComment();

        this.fortuneName = article.getFortune().getFortuneName();  // getFortune -> getFortunes
        this.fortuneImg = article.getFortune().getFortuneImg();
        this.createdAt = article.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

}
