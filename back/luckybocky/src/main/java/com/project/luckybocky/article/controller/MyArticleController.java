package com.project.luckybocky.article.controller;

import com.project.luckybocky.article.dto.MyArticlesDto;
import com.project.luckybocky.article.service.MyArticleService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/article/user")
@RequiredArgsConstructor
public class MyArticleController {
    private final MyArticleService myArticleService;

    @GetMapping
    public ResponseEntity<MyArticlesDto> myArticle(HttpSession session){
//        String userKey = (String) session.getAttribute("user");
        String userKey ="changhee9";

        MyArticlesDto myArticles = myArticleService.findMyArticles(userKey);
        return ResponseEntity.status(HttpStatus.OK).body(myArticles);
    }

}
