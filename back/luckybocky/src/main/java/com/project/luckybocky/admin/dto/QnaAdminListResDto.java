package com.project.luckybocky.admin.dto;

import org.springframework.data.domain.Page;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QnaAdminListResDto {
	private Page<QnaAdminDto> qnaBackOfficeResDto;

	public static QnaAdminListResDto toQnaBackOfficeListResDto(Page<QnaAdminDto> qnaBackOfficeResDtoList) {
		return QnaAdminListResDto.builder()
			.qnaBackOfficeResDto(qnaBackOfficeResDtoList)
			.build();
	}
}
