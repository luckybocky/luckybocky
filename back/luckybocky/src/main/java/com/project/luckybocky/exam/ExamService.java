package com.project.luckybocky.exam;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class ExamService {
    ResponseEntity<ExamDataResponseDto<ExamDto>> exam() {
        return ExamDataResponseDto.of(new ExamDto());
    }

    ResponseEntity<ExamResponseDto> exam2() {
        return ExamResponseDto.success();
    }
}