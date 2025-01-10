package com.project.luckybocky.feedback.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
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
	private Integer feedbackSeq;
	private Integer userSeq;
	private String feedbackContent;
	@Schema(type = "byte", format = "int8", description = "Feedback rate as a byte value")
	private Byte feedbackRate;
	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;
	private boolean isDeleted;
}
