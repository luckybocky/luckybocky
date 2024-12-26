package com.project.luckybocky.article.dto;

import com.project.luckybocky.article.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ArticleResponseDto {
    private int visibility;     // 공개여부

    private Integer userSeq;
    private String nickname;
    private String content;
    private String comment;

    private int fortuneSeq;
    private String fortuneName;
    private String fortuneImgUrl;

    public ArticleResponseDto(Article article){
        this.visibility = article.getArticleVisibility();
        this.userSeq = (article.getUser() != null) ? article.getUser().getUserSeq() : null;
        this.nickname = article.getUserNickname();
        this.content = article.getArticleContent();
        this.comment = article.getArticleComment();
        this.fortuneSeq = article.getFortune().getFortuneSeq();
        this.fortuneName = article.getFortune().getFortuneName();  // getFortune -> getFortunes
        this.fortuneImgUrl = article.getFortune().getFortuneImg();
    }
}
