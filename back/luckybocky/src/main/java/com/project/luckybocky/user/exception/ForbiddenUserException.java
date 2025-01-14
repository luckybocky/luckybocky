package com.project.luckybocky.user.exception;

import com.project.luckybocky.common.CustomException;

public class ForbiddenUserException extends CustomException {
    public ForbiddenUserException(String message){
        super(message);
    }
}
