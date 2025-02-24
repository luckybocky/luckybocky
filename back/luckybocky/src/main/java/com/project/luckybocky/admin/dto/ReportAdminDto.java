package com.project.luckybocky.admin.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportAdminDto {
	private Integer reportSeq;
	private String reportContent;
	private byte reportType;
	private Integer articleSeq;
	private Integer offenderSeq;
	private Integer reporterSeq;
	private String articleContent;
	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;
	private Boolean isDeleted;
}
