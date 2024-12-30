package com.project.luckybocky.article.controller;

import com.project.luckybocky.article.dto.ArticleResponseDto;
import com.project.luckybocky.article.dto.WriteArticleDto;
import com.project.luckybocky.article.service.ArticleService;
import com.project.luckybocky.common.MessageDto;
import com.project.luckybocky.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/article")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;
    private final UserService userService;

    @Description("특정 복 상세 조회")
    @GetMapping
    public ResponseEntity<ArticleResponseDto> getArticleDetails(@RequestParam int articleSeq){
        ArticleResponseDto articleResponseDto = articleService.getArticleDetails(articleSeq);
        return ResponseEntity.status(HttpStatus.OK).body(articleResponseDto);
    }

    @Description("복 달기")
    @PostMapping
    public ResponseEntity<MessageDto> writeArticle(HttpSession session, @RequestBody WriteArticleDto writeArticleDto){
        String userKey = (String) session.getAttribute("user");
        articleService.createArticle(userKey, writeArticleDto);
        return ResponseEntity.status(HttpStatus.OK).body(
                MessageDto.builder()
                        .status("success")
                        .message("복 달기 성공")
                        .build()
        );
    }

    @Description("복 삭제")
    @DeleteMapping
    public ResponseEntity<MessageDto> deleteArticle(HttpSession session, @RequestParam int articleSeq){
        String userKey = (String) session.getAttribute("user");

        if (articleService.getOwnerByArticle(articleSeq) != userService.getUserSeq(userKey)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageDto("fail", "허가되지 않은 사용자"));
        }

        articleService.deleteArticle(articleSeq);
        return ResponseEntity.status(HttpStatus.OK).body(new MessageDto("success", "삭제 성공"));
    }
}
