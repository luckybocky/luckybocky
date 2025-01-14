package com.project.luckybocky.user.exception;

import com.project.luckybocky.common.CustomException;

import lombok.Getter;

@Getter
public class ForbiddenUserException extends CustomException {
    private static final int statusCode = 403;
    private static final String message = "복주머니에 접근 할 권한이 없습니다.";
    public ForbiddenUserException(){
        super(statusCode,message);
    }
}
