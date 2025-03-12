package com.project.luckybocky.admin.dto;

import org.springframework.data.domain.Page;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FeedbackAdminListResDto {
	private Page<FeedbackAdminDto> feedbackBackOfficeResDto;
	private double avgRate;

	public static FeedbackAdminListResDto toFeedbackBackOfficeResDto(
		Page<FeedbackAdminDto> feedbackBackOfficeDtoList, double avgRate) {
		return FeedbackAdminListResDto.builder()
			.feedbackBackOfficeResDto(feedbackBackOfficeDtoList)
			.avgRate(avgRate)
			.build();
	}
}
