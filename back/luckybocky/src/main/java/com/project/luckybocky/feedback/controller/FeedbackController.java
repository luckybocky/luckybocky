package com.project.luckybocky.feedback.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.luckybocky.common.Message;
import com.project.luckybocky.feedback.dto.FeedbackDto;
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

	@PostMapping("/feedback")
	public ResponseEntity<Message> saveFeedback(@RequestBody FeedbackReqDto feedback, HttpSession session) {
		FeedbackDto feedbackDto = feedbackService.saveFeedback(feedback, session);
		if(feedbackDto != null) {
			return ResponseEntity
				.status(HttpStatus.OK)
				.body(new Message(HttpStatus.OK, "피드백 저장 성공", feedbackDto));
		}

		return ResponseEntity
			.status(HttpStatus.NOT_FOUND)
			.body(new Message(HttpStatus.NOT_FOUND, "피드백 저장 실패", null));
	}

	@GetMapping("/feedback")
	public ResponseEntity<Message> getFeedback() {
		FeedbackResDto response = feedbackService.getFeedback();
		
		if(response != null) {
			return ResponseEntity
				.status(HttpStatus.OK)
				.body(new Message(HttpStatus.OK, "피드백 조회 성공", response));
		}
		
		return ResponseEntity
			.status(HttpStatus.NOT_FOUND)
			.body(new Message(HttpStatus.NOT_FOUND, "피드백 없음", null));
	}
}
