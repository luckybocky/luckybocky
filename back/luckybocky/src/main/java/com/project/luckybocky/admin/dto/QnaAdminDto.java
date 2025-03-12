package com.project.luckybocky.admin.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QnaAdminDto {
	private Integer qnaSeq;
	private Integer userSeq;
	private String title;
	private String content;
	private boolean secretStatus;
	private String answer;
	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;
	private Boolean isDeleted;
}
