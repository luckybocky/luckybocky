package com.project.luckybocky.article.dto;

import com.project.luckybocky.article.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ArticleDto {
    private int visibility;
    private String nickname;
    private String content;
    private String comment;
    private String fortune;
    private String imgUrl;

    public ArticleDto(Article article){
        this.visibility = article.getArticleVisibility();
        this.nickname = article.getUserNickname();
        this.content = article.getArticleContent();
        this.comment = article.getArticleComment();
        this.fortune = article.getFortunes().getFortuneName();  // getFortune -> getFortunes
        this.imgUrl = article.getFortunes().getFortuneImg();
    }
}
