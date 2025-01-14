package com.project.luckybocky.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@Getter
@JsonIgnoreProperties({"stackTrace", "cause", "localizedMessage", "suppressed"})
public class CustomException extends RuntimeException {
    public CustomException(String message){
        super(message);
    }
}