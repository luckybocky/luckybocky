package com.project.luckybocky.pocket.exception;

import com.project.luckybocky.common.CustomException;

import lombok.Getter;

@Getter
public class PocketNotFoundException extends CustomException {
    private static final int statusCode = 404;
    private static final String message = "복주머니를 찾을 수 없습니다.";
    public PocketNotFoundException(){
        super(statusCode,message);
    }
}
