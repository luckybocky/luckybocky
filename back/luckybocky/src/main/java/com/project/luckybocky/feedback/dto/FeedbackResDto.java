package com.project.luckybocky.feedback.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FeedbackResDto {
	private List<FeedbackDto> feedbacks;
}
