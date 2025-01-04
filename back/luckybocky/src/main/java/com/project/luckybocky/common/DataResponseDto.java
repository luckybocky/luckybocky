package com.project.luckybocky.common;

import lombok.Builder;
import lombok.experimental.SuperBuilder;

@Builder
public class DataResponseDto<T>{
    private T data;
    private String message;
}
