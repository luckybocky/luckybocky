package com.project.luckybocky.article.exception;

public class CommentConflictException extends RuntimeException{
    public CommentConflictException(String message){
        super(message);
    }
}
