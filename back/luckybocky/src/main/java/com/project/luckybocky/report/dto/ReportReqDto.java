package com.project.luckybocky.report.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReportReqDto {
	private Integer articleSeq;
	private byte reportType;
	private String reportContent;
}
