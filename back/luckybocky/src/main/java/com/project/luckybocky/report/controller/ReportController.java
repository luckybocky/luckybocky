package com.project.luckybocky.report.controller;

import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.luckybocky.common.DataResponseDto;
import com.project.luckybocky.common.NicknameNotFoundException;
import com.project.luckybocky.common.ResponseDto;
import com.project.luckybocky.common.SessionNotFoundException;
import com.project.luckybocky.report.dto.ReportReqDto;
import com.project.luckybocky.report.dto.ReportResDto;
import com.project.luckybocky.report.exception.ReportNotFoundException;
import com.project.luckybocky.report.service.ReportService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "api/v1/report", produces = "application/json; charset=UTF8")
@RequiredArgsConstructor
@Tag(name = "report", description = "신고 저장/조회 API")
public class ReportController {
	private final ReportService reportService;

	@Operation(
		summary = "신고 저장",
		description = "게시글 신고 저장"
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Feedback success"),
		@ApiResponse(responseCode = "400", description = "User nickname is null, unable to save feedback",
			content = @Content(schema = @Schema(implementation = NicknameNotFoundException.class))),
		@ApiResponse(responseCode = "401", description = "User session not found, unable to save feedback",
			content = @Content(schema = @Schema(implementation = SessionNotFoundException.class)))
	})
	@PostMapping()
	public ResponseEntity<ResponseDto> saveReport(@RequestBody ReportReqDto report, HttpSession session) {
		reportService.saveReport(report, session);

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(new ResponseDto("Report success"));
	}

	@Operation(
		summary = "신고 조회",
		description = "신고 내역 조회"
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Report inquiry success"),
		@ApiResponse(responseCode = "404", description = "Report is nothing",
			content = @Content(schema = @Schema(implementation = ReportNotFoundException.class)))
	})
	@GetMapping()
	public ResponseEntity<DataResponseDto<ReportResDto>> getReport() {
		ReportResDto reportResDto = reportService.getReport();

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(new DataResponseDto<>("Report inquiry success", reportResDto));
	}
}
