package com.project.luckybocky.exam;

import com.project.luckybocky.exam.exception.ExamException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/exam", produces = "application/json; charset=UTF8")
@Tag(name = "exam", description = "예시 API")
public class ExamController {

    private final ExamService examService;

    @Operation(
            summary = "예시 데이터 요청",
            description = "예시 데이터를 가져온다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success."),
            @ApiResponse(responseCode = "401", description = "토큰 인증 실패.",
                    content = @Content(schema = @Schema(implementation = ExamException.class))),
            @ApiResponse(responseCode = "403", description = "리프레쉬 토큰 인증 실패.",
                    content = @Content(schema = @Schema(implementation = ExamException.class))),
            @ApiResponse(responseCode = "410", description = "S3 요청 실패.",
                    content = @Content(schema = @Schema(implementation = ExamException.class)))
    })
    @GetMapping
    public ResponseEntity<ExamDataResponseDto<ExamDto>> exam(@RequestBody ExamDto request) {
        return examService.exam();
    }

    @Operation(
            summary = "예시 단순 요청",
            description = "예시 요청을 날린다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success."),
            @ApiResponse(responseCode = "400", description = "요청 받아야 할 값이 없음.",
                    content = @Content(schema = @Schema(implementation = ExamException.class))),
            @ApiResponse(responseCode = "401", description = "토큰 인증 실패.",
                    content = @Content(schema = @Schema(implementation = ExamException.class))),
            @ApiResponse(responseCode = "403", description = "리프레쉬 토큰 인증 실패.",
                    content = @Content(schema = @Schema(implementation = ExamException.class))),
            @ApiResponse(responseCode = "410", description = "S3 요청 실패.",
                    content = @Content(schema = @Schema(implementation = ExamException.class)))
    })
    @PostMapping
    public ResponseEntity<ExamResponseDto> exam2() {
        return examService.exam2();
    }
}