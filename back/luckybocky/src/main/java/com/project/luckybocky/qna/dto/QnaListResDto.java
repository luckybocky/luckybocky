package com.project.luckybocky.qna.dto;

import org.springframework.data.domain.Page;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QnaListResDto {
	private Page<QnaDto> qnaListResDto;

	public static QnaListResDto toQnaResDto(Page<QnaDto> qnaDtoList) {
		return QnaListResDto.builder()
			.qnaListResDto(qnaDtoList)
			.build();
	}
}
