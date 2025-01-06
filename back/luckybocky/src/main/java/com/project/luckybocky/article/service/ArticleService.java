package com.project.luckybocky.article.service;

import com.project.luckybocky.article.dto.ArticleResponseDto;
import com.project.luckybocky.article.dto.CommentDto;
import com.project.luckybocky.article.dto.WriteArticleDto;
import com.project.luckybocky.article.entity.Article;
import com.project.luckybocky.article.exception.ArticleNotFoundException;
import com.project.luckybocky.article.exception.CommentConflictException;
import com.project.luckybocky.article.repository.ArticleRepository;
import com.project.luckybocky.fortune.entity.Fortune;
import com.project.luckybocky.fortune.exception.FortuneNotFoundException;
import com.project.luckybocky.fortune.repository.FortuneRepository;
import com.project.luckybocky.pocket.entity.Pocket;
import com.project.luckybocky.pocket.exception.PocketNotFoundException;
import com.project.luckybocky.pocket.repository.PocketRepository;
import com.project.luckybocky.user.entity.User;
import com.project.luckybocky.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                .orElseThrow(() -> new ArticleNotFoundException("not found article"));
        return new ArticleResponseDto(article);
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
                .orElseThrow(() -> new FortuneNotFoundException("Invalid fortuneSeq: " + writeArticleDto.getFortuneSeq()));

        Pocket pocket = pocketRepository.findPocketByPocketSeq(writeArticleDto.getPocketSeq())
            .orElseThrow(() -> new PocketNotFoundException("Invalid pocketSeq: " + writeArticleDto.getPocketSeq()));

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
        Article article = articleRepository.findByArticleSeq(findArticle)
                .orElseThrow(() -> new ArticleNotFoundException("not found article"));
        if (article.getArticleComment() != null){
            throw new CommentConflictException("Already exist comment");
        }
        article.updateComment(commentDto.getComment());
        articleRepository.save(article);

        return new ArticleResponseDto(article);
    }

    public void deleteArticle(int articleSeq) {
        Article findArticle = articleRepository.findByArticleSeq(articleSeq)
                .orElseThrow(() -> new ArticleNotFoundException("not found article"));
        articleRepository.delete(findArticle);
    }

    public int getOwnerByArticle(int articleSeq){
        Article findArticle = articleRepository.findByArticleSeq(articleSeq)
                .orElseThrow(() -> new ArticleNotFoundException("not found article"));
        Pocket pocket = findArticle.getPocket();
        return pocket.getUser().getUserSeq();
    }

    // 모든 복을 가져오기 (공개여부 체크 X)
    public List<ArticleResponseDto> getAllArticles(int pocketSeq){
        Pocket pocket = pocketRepository.findPocketByPocketSeq(pocketSeq)
                .orElseThrow(() -> new PocketNotFoundException("not found pocket"));
        List<ArticleResponseDto> result = pocket.getArticles().stream()
                .map(a -> new ArticleResponseDto(a))
                .collect(Collectors.toList());
        return result;
    }

    // 모든 복의 공개여부 체크 후 가져오기
    public List<ArticleResponseDto> getAllArticlesCheck(int pocketSeq) {
        Pocket pocket = pocketRepository.findPocketByPocketSeq(pocketSeq)
                .orElseThrow(() -> new PocketNotFoundException("not found pocket"));
        List<ArticleResponseDto> result = pocket.getArticles().stream()
                .map(article -> {
                    ArticleResponseDto dto = new ArticleResponseDto(article);
                    if (!dto.isArticleVisibility()) {
                        dto.setArticleContent("비밀글입니다.");
                        dto.setArticleComment("비밀글입니다.");
                    }
                    return dto;
                })
                .collect(Collectors.toList());
        return result;
    }

    // 모든 복을 비공개로 가져오기
    public List<ArticleResponseDto> getAllArticlesInvisible(int pocketSeq) {
        Pocket pocket = pocketRepository.findPocketByPocketSeq(pocketSeq)
                .orElseThrow(() -> new PocketNotFoundException("not found pocket"));
        List<ArticleResponseDto> result = pocket.getArticles().stream()
                .map(article -> {
                    ArticleResponseDto dto = new ArticleResponseDto(article);
                    dto.setArticleContent("비밀글입니다.");
                    dto.setArticleComment("비밀글입니다.");
                    return dto;
                })
                .collect(Collectors.toList());
        return result;
    }

//    public List<ArticleResponseDto> getArticlesByUser(String userKey){
//        User user = userRepository.findByUserKey(userKey)
//                .orElseThrow(() -> new UserNotFoundException("not found user"));
//        Pocket pocket = pocketRepository.findFirstByUserOrderByCreatedAtDesc(user)
//                .orElseThrow(() -> new PocketNotFoundException("not found pocket"));
//        List<ArticleResponseDto> result = pocket.getArticles().stream()
//                .map(a -> new ArticleResponseDto(a))
//                .collect(Collectors.toList());
//        return result;
//    }
}
