package com.project.luckybocky.exam;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExamDataResponseDto<T> extends ExamResponseDto {
    @Schema(description = "응답 데이터")
    private T data;

    public static <T> ResponseEntity<ExamDataResponseDto<T>> of(T data) {
        ExamDataResponseDto<T> responseBody = new ExamDataResponseDto<>(data);
        return
                ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}