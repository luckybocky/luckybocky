package com.project.luckybocky.exam;

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
public class ExamResponseDto {
    private String message = "Success";

    public static ResponseEntity<ExamResponseDto> success() {
        ExamResponseDto responseBody = new ExamResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}