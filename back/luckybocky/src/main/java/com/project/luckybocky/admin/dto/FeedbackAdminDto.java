package com.project.luckybocky.admin.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedbackAdminDto {
	private Integer feedbackSeq;
	private String feedbackContent;
	private Byte feedbackRate;
	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;
	private Boolean isDeleted;
}
