package com.project.luckybocky.fortune.exception;

import com.project.luckybocky.common.CustomException;

import lombok.Getter;

@Getter
public class FortuneNotFoundException extends CustomException {
    private static final int statusCode = 404;
    private static final String message = "포춘을 찾을 수 없습니다.";
    public FortuneNotFoundException(){
        super(statusCode,message);
    }
}
