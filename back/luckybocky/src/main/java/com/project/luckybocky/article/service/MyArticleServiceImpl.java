package com.project.luckybocky.article.service;

import com.project.luckybocky.article.dto.MyArticleDto;
import com.project.luckybocky.article.dto.MyArticlesDto;
import com.project.luckybocky.article.entity.Article;
import com.project.luckybocky.article.repository.MyArticleRepository;
import com.project.luckybocky.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class MyArticleServiceImpl implements MyArticleService {
    private final MyArticleRepository myArticleRepository;

    @Override
    public MyArticlesDto findMyArticles(String userKey) {

        if(userKey == null){
            throw new UserNotFoundException();
        }

        List<Article> articleByUserKey = myArticleRepository.findArticleByUserKey(userKey);
        List<MyArticleDto> myArticles = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (Article article : articleByUserKey) {
            myArticles.add(MyArticleDto
                    .builder()
                    .pocketOwner(article.getPocket().getUser().getUserNickname())
                    .articleOwner(article.getUserNickname())
                    .content(article.getArticleContent())
                    .comment(article.getArticleComment())
                    .fortuneName(article.getFortune().getFortuneName())
                    .fortuneImg(article.getFortune().getFortuneSeq())
                    .createdAt(article.getCreatedAt().format(formatter))
                    .build());
        }

        return new MyArticlesDto(myArticles);
    }
}
