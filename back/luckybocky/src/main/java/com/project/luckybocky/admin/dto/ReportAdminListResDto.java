package com.project.luckybocky.admin.dto;

import org.springframework.data.domain.Page;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReportAdminListResDto {
	private Page<ReportAdminDto> reportBackOfficeResDto;

	public static ReportAdminListResDto toReportBackOfficeListResDto(
		Page<ReportAdminDto> reportBackOfficeDtoList) {
		return ReportAdminListResDto.builder()
			.reportBackOfficeResDto(reportBackOfficeDtoList)
			.build();
	}
}
