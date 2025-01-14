package com.project.luckybocky.article.exception;

import com.project.luckybocky.common.CustomException;

import lombok.Getter;

@Getter
public class ArticleNotFoundException extends CustomException {
    private static final int statusCode = 409;
    private static final String message = "복을 찾을 수 없습니다.";
    public ArticleNotFoundException(){
        super(statusCode,message);
    }
}
