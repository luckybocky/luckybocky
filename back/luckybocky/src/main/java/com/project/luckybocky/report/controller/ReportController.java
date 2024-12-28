package com.project.luckybocky.report.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.luckybocky.common.Message;
import com.project.luckybocky.report.dto.ReportDto;
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

	@PostMapping("report")
	public ResponseEntity<Message> saveReport(@RequestBody ReportReqDto report, HttpSession session) {
		ReportDto reportDto = reportService.saveReport(report, session);
		if(reportDto != null) {
			return ResponseEntity
				.status(HttpStatus.OK)
				.body(new Message(HttpStatus.OK, "신고 저장 성공", reportDto));
		}

		return ResponseEntity
			.status(HttpStatus.NOT_FOUND)
			.body(new Message(HttpStatus.NOT_FOUND, "신고 저장 실패", null));
	}

	@GetMapping("report")
	public ResponseEntity<Message> getReport() {
		ReportResDto reportResDto = reportService.getReport();
		if(reportResDto != null) {
			return ResponseEntity
				.status(HttpStatus.OK)
				.body(new Message(HttpStatus.OK, "신고 조회 성공", reportResDto));
		}

		return ResponseEntity
			.status(HttpStatus.NOT_FOUND)
			.body(new Message(HttpStatus.NOT_FOUND, "신고 없음", null));
	}
}
