package com.project.luckybocky.feedback.controller;

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
import com.project.luckybocky.feedback.dto.FeedbackReqDto;
import com.project.luckybocky.feedback.dto.FeedbackResDto;
import com.project.luckybocky.feedback.service.FeedbackService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class FeedbackController {
	private final FeedbackService feedbackService;

	@Description("피드백 저장")
	@PostMapping("/feedback")
	public ResponseEntity<ResponseDto> saveFeedback(@RequestBody FeedbackReqDto feedback, HttpSession session) {
		feedbackService.saveFeedback(feedback, session);

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(new ResponseDto("Feedback success"));
	}

	@Description("피드백 조회")
	@GetMapping("/feedback")
	public ResponseEntity<DataResponseDto<FeedbackResDto>> getFeedback() {
		FeedbackResDto response = feedbackService.getFeedback();

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(new DataResponseDto<>("Feedback inquiry success", response));
	}
}
