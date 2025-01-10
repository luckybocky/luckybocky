package com.project.luckybocky.article.repository;

import com.project.luckybocky.article.entity.Article;

import java.util.List;

public interface MyArticleRepository {

    List<Article> findArticleByUserKey(String userKey);

    Article findByArticleSeq(int articleSeq);
}
