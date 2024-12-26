package com.project.luckybocky.article.controller;

import com.project.luckybocky.article.dto.ArticleResponseDto;
import com.project.luckybocky.article.dto.CommentDto;
import com.project.luckybocky.article.service.ArticleService;
import com.project.luckybocky.common.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
public class CommentController {
    private final ArticleService articleService;

    @PostMapping
    public ResponseEntity<MessageDto> writeComment(@RequestBody CommentDto commentDto){
        ArticleResponseDto responseDto = articleService.updateComment(commentDto);
        // 여기에 알림 넣으면 될듯함니다.
        return ResponseEntity.status(HttpStatus.OK).body(new MessageDto("success", "답변 작성 성공"));
    }

}
