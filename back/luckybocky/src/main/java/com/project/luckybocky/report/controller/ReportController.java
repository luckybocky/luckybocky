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
import com.project.luckybocky.common.ResponseDto;
import com.project.luckybocky.report.dto.ReportReqDto;
import com.project.luckybocky.report.dto.ReportResDto;
import com.project.luckybocky.report.service.ReportService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/")
@RequiredArgsConstructor
public class ReportController {
	private final ReportService reportService;

	@Description("신고 저장")
	@PostMapping("report")
	public ResponseEntity<ResponseDto> saveReport(@RequestBody ReportReqDto report, HttpSession session) {
		reportService.saveReport(report, session);

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(new ResponseDto("Report success"));
	}

	@Description("신고 조회")
	@GetMapping("report")
	public ResponseEntity<DataResponseDto<ReportResDto>> getReport() {
		ReportResDto reportResDto = reportService.getReport();

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(new DataResponseDto<>("Report inquiry success", reportResDto));
	}
}
