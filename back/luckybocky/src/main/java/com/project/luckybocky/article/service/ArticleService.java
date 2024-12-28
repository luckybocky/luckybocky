package com.project.luckybocky.article.service;

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

    public ArticleResponseDto getArticleDetails(int articleSeq){
        Article article = articleRepository.findByArticleSeq(articleSeq)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "글이 없습니다."));
        ArticleResponseDto articleResponseDto = new ArticleResponseDto(article);

        return articleResponseDto;
    }

    // 사용자가 보유한 복주머니 가져오기
//    public List<ArticleResponseDto> getAllArticlesByDate(int userSeq, int year){
//        LocalDate startDate = LocalDate.of(year, 7, 1);
//        Pocket findPocket = pocketRepository.findPocketByUserAndDate(userSeq, startDate, LocalDate.now())
//                .orElseThrow(() -> new NullPointerException("복주머니를 찾지 못했습니다."));
//        List<ArticleResponseDto> result = findPocket.getArticles().stream()
//                .map(a -> new ArticleResponseDto(a))
//                .collect(Collectors.toList());
//
//        return result;
//    }

    public void createArticle(String userKey, WriteArticleDto writeArticleDto) {
        Optional<User> user = userRepository.findByUserKey(userKey);
//        if (user.isEmpty()){
//            throw new IllegalArgumentException("Invalid userKey: " + userKey);
//        }

        Fortune fortune = fortuneRepository.findByFortuneSeq(writeArticleDto.getFortuneSeq())
                .orElseThrow(() -> new IllegalArgumentException("Invalid fortuneSeq: " + writeArticleDto.getFortuneSeq()));

        Pocket pocket = pocketRepository.findPocketByPocketSeq(writeArticleDto.getPocketSeq())
            .orElseThrow(() -> new IllegalArgumentException("Invalid pocketSeq: " + writeArticleDto.getPocketSeq()));

        Article article = Article.builder()
                .user(user.orElse(null))
                .userNickname(writeArticleDto.getNickname())
                .articleContent(writeArticleDto.getContent())
                .articleComment(null)
                .articleVisibility(writeArticleDto.isVisibility() ? 1 : 0)
                .fortune(fortune)
                .pocket(pocket)
                .build();

        articleRepository.save(article);
    }

    public ArticleResponseDto updateComment(CommentDto commentDto){
        int findArticle = commentDto.getArticleSeq();
        Article article = articleRepository.findByArticleSeq(findArticle).get();
        if (article.getArticleComment() != null){
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

    // 복주머니 Seq에 맞는 article 가져오기
    public List<ArticleResponseDto> getArticlesByPocket(int pocketSeq){
        Pocket pocket = pocketRepository.findPocketByPocketSeq(pocketSeq)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 복주머니입니다."));
        List<ArticleResponseDto> result = pocket.getArticles().stream()
                .map(a -> new ArticleResponseDto(a))
                .collect(Collectors.toList());
        return result;
    }

    public List<ArticleResponseDto> getArticlesByUser(String userKey){
        User user = userRepository.findByUserKey(userKey)
                .orElseThrow(() -> new IllegalArgumentException("부적절한 사용자입니다."));
        Pocket pocket = pocketRepository.findFirstByUserOrderByCreatedAtDesc(user)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 복주머니입니다."));
        List<ArticleResponseDto> result = pocket.getArticles().stream()
                .map(a -> new ArticleResponseDto(a))
                .collect(Collectors.toList());
        return result;
    }
}
