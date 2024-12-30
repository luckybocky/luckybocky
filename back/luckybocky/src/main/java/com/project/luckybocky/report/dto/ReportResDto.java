package com.project.luckybocky.report.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReportResDto {
	private List<ReportDto> reports;
}
