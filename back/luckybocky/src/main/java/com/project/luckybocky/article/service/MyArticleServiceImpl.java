package com.project.luckybocky.article.service;

import com.project.luckybocky.article.dto.MyArticleDto;
import com.project.luckybocky.article.dto.MyArticlesDto;
import com.project.luckybocky.article.entity.Article;
import com.project.luckybocky.article.repository.MyArticleRepository;
import jakarta.persistence.Entity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class MyArticleServiceImpl implements MyArticleService{
    private final MyArticleRepository myArticleRepository;

    @Override
    public MyArticlesDto findMyArticles(String userKey) {
        List<Article> articleByUserKey = myArticleRepository.findArticleByUserKey(userKey);
        List<MyArticleDto> myArticles = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for(Article article : articleByUserKey){
            String articleOwner = article.getUserNickname();
            String content = article.getArticleContent();
            String fortuneName = article.getFortune().getFortuneName();
            String fortuneImg = article.getFortune().getFortuneImg();
            String createdAt =  article.getCreatedAt().format(formatter);

            myArticles.add(new MyArticleDto(articleOwner,content,fortuneName,fortuneImg,createdAt));
        }

        return new MyArticlesDto(myArticles);
    }
}
