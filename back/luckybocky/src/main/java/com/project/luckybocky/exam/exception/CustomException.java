package com.project.luckybocky.exam.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@Getter
//swagger 예외 표시를 간단하게 하기 위해
@JsonIgnoreProperties({"stackTrace", "cause", "localizedMessage", "suppressed"})
public class CustomException extends RuntimeException {
    private int statusCode;
    private String message;
}