package com.project.luckybocky.article.service;

import com.project.luckybocky.user.entity.User;

public interface ArticlePushService {
    User findArticleOwner(int articleSeq);
}
