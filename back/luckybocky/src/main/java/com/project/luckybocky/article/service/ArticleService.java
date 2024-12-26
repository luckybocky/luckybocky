package com.project.luckybocky.article.service;

import com.project.luckybocky.article.dto.ArticleDto;
import com.project.luckybocky.article.entity.Article;
import com.project.luckybocky.article.repository.ArticleRepository;
import com.project.luckybocky.fortune.repository.FortuneRepository;
import com.project.luckybocky.pocket.entity.Pocket;
import com.project.luckybocky.pocket.repository.PocketRepository;
import com.project.luckybocky.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final PocketRepository pocketRepository;

    public ArticleDto getArticle(int articleSeq){
        Article article = articleRepository.findByArticleSeq(articleSeq)
                .orElseThrow(() -> new NullPointerException("글이 없습니다."));
        ArticleDto articleDto = new ArticleDto(article);

        return articleDto;
    }

    public List<ArticleDto> getAllArticlesByDate(int userSeq, int year){
        LocalDate startDate = LocalDate.of(year, 7, 1);
        Pocket findPocket = pocketRepository.findPocketByUserAndDate(userSeq, startDate, LocalDate.now())
                .orElseThrow(() -> new NullPointerException("복주머니를 찾지 못했습니다."));
        List<Article> findArticles = articleRepository.findAllByPocket(findPocket);
        List<ArticleDto> result = findArticles.stream()
                .map(a -> new ArticleDto(a))
                .collect(Collectors.toList());
        return result;
    }
}
