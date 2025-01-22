package com.project.luckybocky.article.exception;

import com.project.luckybocky.common.CustomException;

import lombok.Getter;

@Getter
public class CommentConflictException extends CustomException {
    private static final int statusCode = 409;
    private static final String message = "이미 복을 넣었습니다.";
    public CommentConflictException(){
        super(statusCode,message);


    }
}
