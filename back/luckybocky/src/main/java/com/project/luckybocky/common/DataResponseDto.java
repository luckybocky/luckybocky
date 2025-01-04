package com.project.luckybocky.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DataResponseDto<T> extends ResponseDto {
    private T data;
}
