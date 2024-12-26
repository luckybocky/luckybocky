package com.project.luckybocky.article.service;

import com.google.api.Http;
import com.project.luckybocky.article.dto.ArticleResponseDto;
import com.project.luckybocky.article.dto.CommentDto;
import com.project.luckybocky.article.dto.WriteArticleDto;
import com.project.luckybocky.article.entity.Article;
import com.project.luckybocky.article.repository.ArticleRepository;
import com.project.luckybocky.fortune.entity.Fortune;
import com.project.luckybocky.fortune.repository.FortuneRepository;
import com.project.luckybocky.pocket.entity.Pocket;
import com.project.luckybocky.pocket.repository.PocketRepository;
import com.project.luckybocky.user.entity.User;
import com.project.luckybocky.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final PocketRepository pocketRepository;
    private final UserRepository userRepository;
    private final FortuneRepository fortuneRepository;

    public ArticleResponseDto getArticle(int articleSeq){
        Article article = articleRepository.findByArticleSeq(articleSeq)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "글이 없습니다."));
        ArticleResponseDto articleResponseDto = new ArticleResponseDto(article);

        return articleResponseDto;
    }

    public List<ArticleResponseDto> getAllArticlesByDate(int userSeq, int year){
        LocalDate startDate = LocalDate.of(year, 7, 1);
        Pocket findPocket = pocketRepository.findPocketByUserAndDate(userSeq, startDate, LocalDate.now())
                .orElseThrow(() -> new NullPointerException("복주머니를 찾지 못했습니다."));
        List<Article> findArticles = articleRepository.findAllByPocket(findPocket);
        List<ArticleResponseDto> result = findArticles.stream()
                .map(a -> new ArticleResponseDto(a))
                .collect(Collectors.toList());
        return result;
    }

    public void createArticle(WriteArticleDto writeArticleDto) {
        int findUserSeq = writeArticleDto.getUserSeq();
        Optional<User> user = userRepository.findByUserSeq(findUserSeq);
        if (user.isEmpty()){
            throw new IllegalArgumentException("Invalid userSeq: " + writeArticleDto.getUserSeq());
        }

        Fortune fortune = fortuneRepository.findByFortuneSeq(writeArticleDto.getFortuneSeq())
                .orElseThrow(() -> new IllegalArgumentException("Invalid fortuneSeq: " + writeArticleDto.getFortuneSeq()));

        Pocket pocket = pocketRepository.findPocketByPocketSeq(writeArticleDto.getPocketSeq())
            .orElseThrow(() -> new IllegalArgumentException("Invalid pocketSeq: " + writeArticleDto.getPocketSeq()));

        Article article = Article.builder()
                .user(user.orElse(null))
                .userNickname(writeArticleDto.getNickname())
                .articleContent(writeArticleDto.getContent())
                .articleComment(null)
                .articleVisibility(writeArticleDto.getVisibility())
                .fortune(fortune)
                .pocket(pocket)
                .build();

        articleRepository.save(article);
    }

    public ArticleResponseDto updateComment(CommentDto commentDto){
        int findArticle = commentDto.getArticleSeq();
        Article article = articleRepository.findByArticleSeq(findArticle).get();
        if (!article.getArticleComment().isEmpty()){
            throw new IllegalArgumentException("Already exist comment");
        }
        article.updateComment(commentDto.getComment());
        articleRepository.save(article);

        return new ArticleResponseDto(article);
    }

    public void deleteArticle(int articleSeq) {
        Article findArticle = articleRepository.findByArticleSeq(articleSeq).get();
        articleRepository.delete(findArticle);
    }

    public int getOwnerByArticle(int articleSeq){
        Article findArticle = articleRepository.findByArticleSeq(articleSeq).get();
        Pocket pocket = findArticle.getPocket();
        return pocket.getUser().getUserSeq();
    }
}
