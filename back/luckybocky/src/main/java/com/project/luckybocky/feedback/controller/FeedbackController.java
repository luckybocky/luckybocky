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
import com.project.luckybocky.common.NicknameNotFoundException;
import com.project.luckybocky.common.ResponseDto;
import com.project.luckybocky.common.SessionNotFoundException;
import com.project.luckybocky.exam.exception.ExamException;
import com.project.luckybocky.feedback.dto.FeedbackReqDto;
import com.project.luckybocky.feedback.dto.FeedbackResDto;
import com.project.luckybocky.feedback.exception.FeedbackNotFoundException;
import com.project.luckybocky.feedback.service.FeedbackService;
import com.project.luckybocky.user.exception.UserNotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "api/v1/feedback", produces = "application/json; charset=UTF8")
@RequiredArgsConstructor
@Tag(name = "feedback", description = "피드백 저장/조회 API")
public class FeedbackController {
	private final FeedbackService feedbackService;

	@Operation(
		summary = "피드백 저장",
		description = "사용자 피드백 저장"
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Feedback success"),
		@ApiResponse(responseCode = "400", description = "User nickname is null, unable to save feedback",
			content = @Content(schema = @Schema(implementation = NicknameNotFoundException.class))),
		@ApiResponse(responseCode = "401", description = "User session not found, unable to save feedback",
			content = @Content(schema = @Schema(implementation = SessionNotFoundException.class)))
	})
	@PostMapping()
	public ResponseEntity<ResponseDto> saveFeedback(@RequestBody FeedbackReqDto feedback, HttpSession session) {
		feedbackService.saveFeedback(feedback, session);

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(new ResponseDto("Feedback success"));
	}

	@Operation(
		summary = "피드백 조회",
		description = "저장된 피드백 조회"
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Feedback inquiry success"),
		@ApiResponse(responseCode = "404", description = "Feedback is nothing",
			content = @Content(schema = @Schema(implementation = FeedbackNotFoundException.class)))
	})
	@GetMapping()
	public ResponseEntity<DataResponseDto<FeedbackResDto>> getFeedback() {
		FeedbackResDto response = feedbackService.getFeedback();

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(new DataResponseDto<>("Feedback inquiry success", response));
	}
}
