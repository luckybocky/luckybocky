package com.project.luckybocky.article.controller;

import com.project.luckybocky.article.dto.ArticleResponseDto;
import com.project.luckybocky.article.dto.CommentDto;
import com.project.luckybocky.article.service.ArticleService;
import com.project.luckybocky.common.MessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
@Slf4j
public class CommentController {
    private final ArticleService articleService;

    @PostMapping
    public ResponseEntity<MessageDto> writeComment(@RequestBody CommentDto commentDto){
        articleService.updateComment(commentDto);
        return ResponseEntity.status(HttpStatus.OK).body(new MessageDto("success", "답변 작성 성공"));
    }

}
