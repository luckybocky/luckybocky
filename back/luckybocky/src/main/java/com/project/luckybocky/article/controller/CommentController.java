package com.project.luckybocky.article.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.project.luckybocky.article.dto.CommentDto;
import com.project.luckybocky.article.exception.ArticleNotFoundException;
import com.project.luckybocky.article.service.ArticleService;
import com.project.luckybocky.common.ResponseDto;
import com.project.luckybocky.push.dto.PushDto;
import com.project.luckybocky.push.service.PushService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
@Slf4j
public class CommentController {
    private final ArticleService articleService;
    private final PushService pushService;

    @Operation(
        summary = "복에 리복 달기.",
        description = "복(댓글)에 리복(대댓글)을 한다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "1. 리복 달기 성공 \t\n 2. 복 푸시알림 성공" ),
        @ApiResponse(responseCode = "404", description = "1. 복 조회 실패 ",
            content = @Content(schema = @Schema(implementation = ArticleNotFoundException.class))),
        @ApiResponse(responseCode = "500", description = "1. 푸시알림 전송 실패",
            content = @Content(schema = @Schema(implementation = FirebaseMessagingException.class)))
    })
    @PostMapping
    public ResponseEntity<ResponseDto> writeComment(HttpSession session, @RequestBody CommentDto commentDto) throws
		FirebaseMessagingException {
        articleService.updateComment(commentDto);

        //push 알림추가
        String fromUser = (String) session.getAttribute("nickname" );
        PushDto pushDto = new PushDto(commentDto.getArticleSeq(), fromUser,commentDto.getUrl());
        log.info("{} 님의 리복 푸시 시도 (복 번호 : {})", pushDto.getFromUser(), pushDto.getContentSeq());
        pushService.sendArticlePush(pushDto);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("success"));
    }

}
