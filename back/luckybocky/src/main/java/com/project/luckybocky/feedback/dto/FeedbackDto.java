package com.project.luckybocky.feedback.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class FeedbackDto {
	private Integer userSeq;
	private String feedbackContent;
	private Byte feedbackRate;
	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;
	private boolean isDeleted;
}
