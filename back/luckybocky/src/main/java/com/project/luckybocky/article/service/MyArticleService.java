package com.project.luckybocky.article.service;

import com.project.luckybocky.article.dto.MyArticleDto;
import com.project.luckybocky.article.dto.MyArticlesDto;

public interface MyArticleService {
    MyArticlesDto findMyArticles(String userKey);


}
