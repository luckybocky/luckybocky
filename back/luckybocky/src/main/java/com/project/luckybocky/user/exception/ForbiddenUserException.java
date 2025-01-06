package com.project.luckybocky.user.exception;

public class ForbiddenUserException extends RuntimeException{
    public ForbiddenUserException(String message){
        super(message);
    }
}
