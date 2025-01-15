package com.project.luckybocky.article.controller;

import com.project.luckybocky.article.dto.MyArticlesDto;
import com.project.luckybocky.article.service.MyArticleService;
import com.project.luckybocky.common.DataResponseDto;
import com.project.luckybocky.user.exception.UserNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/article/user" )
@RequiredArgsConstructor
@Slf4j
@Tag(name = "나의 정보 조회", description = "내가 보낸 복 조회")
public class MyArticleController {
    private final MyArticleService myArticleService;

    @Description("내가 보낸 복 보기")
    @Operation(
            summary = "내가 보낸 복 보기",
            description = "다른 사용자의 복주머니에 넣은 복을 조회한다"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "1. 사용자 조회 성공"),
            @ApiResponse(responseCode = "401", description = "1. 사용자를 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = UserNotFoundException.class)))
    })
    @GetMapping
    public ResponseEntity<DataResponseDto<MyArticlesDto>> myArticle(HttpSession session) {
        String userKey = (String) session.getAttribute("user" );
        log.info("내가 보낸 복을 조회 합니다. {}", userKey);
        MyArticlesDto myArticles = myArticleService.findMyArticles(userKey);
        return ResponseEntity.status(HttpStatus.OK).body(new DataResponseDto<>("success", myArticles));
    }

}
