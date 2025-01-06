package com.project.luckybocky.exam;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter //Getter 없으면 swagger에서 속성값을 못 읽어들임
@AllArgsConstructor
@NoArgsConstructor
public class ExamDto {
    @Schema(description = "test int")
    private int test1;
    @Schema(description = "test string")
    private String test2;
    private String test3;
    private String test4;
}