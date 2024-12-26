package com.project.luckybocky.article.controller;

import com.project.luckybocky.article.dto.ArticleResponseDto;
import com.project.luckybocky.article.dto.WriteArticleDto;
import com.project.luckybocky.article.service.ArticleService;
import com.project.luckybocky.common.MessageDto;
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

    @Description("특정 복에 대한 정보를 가져온다.")
    @GetMapping
    public ResponseEntity<ArticleResponseDto> getArticleDetails(@RequestParam int articleSeq){
        ArticleResponseDto articleResponseDto = articleService.getArticle(articleSeq);
        return ResponseEntity.status(HttpStatus.OK).body(articleResponseDto);
    }

    @Description("복을 작성한다.")
    @PostMapping
    public ResponseEntity<MessageDto> writeArticle(@RequestBody WriteArticleDto writeArticleDto){
        articleService.createArticle(writeArticleDto);
        return ResponseEntity.status(HttpStatus.OK).body(
                MessageDto.builder()
                        .status("success")
                        .message("복 달기 성공")
                        .build()
        );
    }

}
