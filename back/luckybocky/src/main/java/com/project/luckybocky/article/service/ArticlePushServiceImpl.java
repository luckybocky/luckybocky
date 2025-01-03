package com.project.luckybocky.article.service;

import com.project.luckybocky.article.entity.Article;
import com.project.luckybocky.article.repository.MyArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ArticlePushServiceImpl implements ArticlePushService{
    private final MyArticleRepository myArticleRepository;

    @Override
    public String findPocketOwner(int articleSeq) {
        Article article = myArticleRepository.findByArticleSeq(articleSeq);

        if(article ==null) throw new IllegalArgumentException("복을 찾을 수 없습니다.");

        return article.getPocket().getUser().getUserKey();
    }
}
