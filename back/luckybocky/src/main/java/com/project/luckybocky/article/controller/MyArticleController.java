package com.project.luckybocky.article.controller;

import com.project.luckybocky.article.dto.MyArticlesDto;
import com.project.luckybocky.article.service.MyArticleService;
import com.project.luckybocky.common.DataResponseDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/article/user" )
@RequiredArgsConstructor
public class MyArticleController {
    private final MyArticleService myArticleService;

    @Description("내가 보낸 복 보기")
    @GetMapping
    public ResponseEntity<DataResponseDto<MyArticlesDto>> myArticle(HttpSession session) {
        String userKey = (String) session.getAttribute("user" );
//        String userKey ="changhee";

        MyArticlesDto myArticles = myArticleService.findMyArticles(userKey);
        return ResponseEntity.status(HttpStatus.OK).body(new DataResponseDto<>("success", myArticles));
    }

}
