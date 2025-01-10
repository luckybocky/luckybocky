package com.project.luckybocky.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DataResponseDto<T> {
    private String message;
    private T data;
}
