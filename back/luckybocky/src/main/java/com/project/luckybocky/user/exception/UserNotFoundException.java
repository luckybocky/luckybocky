package com.project.luckybocky.user.exception;

import com.project.luckybocky.common.CustomException;

import lombok.Getter;

@Getter
public class UserNotFoundException extends CustomException {
    private static final int statusCode = 401;
    private static final String message = "사용자를 찾을 수 없습니다.";
    public UserNotFoundException(){
        super(statusCode,message);
    }

}
