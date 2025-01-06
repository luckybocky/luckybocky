package com.project.luckybocky.common;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DataResponseDto<T> {
    private String message;
    private T data;
}
