package com.project.luckybocky.feedback.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FeedbackReqDto {
	private String feedbackContent;
	private Integer feedbackRate;
}
