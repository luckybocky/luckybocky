package com.project.luckybocky.common;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class MessageDto {
    private String status;
    private String message;
}
