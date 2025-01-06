package com.project.luckybocky.fortune.exception;

public class FortuneNotFoundException extends RuntimeException{
    public FortuneNotFoundException(String message){
        super(message);
    }
}
