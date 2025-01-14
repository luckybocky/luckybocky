package com.project.luckybocky.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;

@Getter
@JsonIgnoreProperties({"stackTrace", "cause", "localizedMessage", "suppressed"})
public class CustomException extends RuntimeException {
    private final int statusCode;
    private final String message;

    public CustomException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
        this.message = message;
    }
}