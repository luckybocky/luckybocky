package com.project.luckybocky.report.dto;

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
public class ReportDto {
	private Integer reportSeq;
	private Integer articleSeq;
	private Integer userSeq;
	private byte reportType;
	private String reportContent;
	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;
	private boolean isDeleted;
}
