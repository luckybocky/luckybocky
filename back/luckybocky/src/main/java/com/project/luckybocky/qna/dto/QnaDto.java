package com.project.luckybocky.qna.dto;

import java.time.LocalDate;

import org.springframework.data.domain.Page;

import com.project.luckybocky.qna.entity.Qna;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QnaDto {
	private Integer qnaSeq;
	private String title;
	private String content;
	private String answer;
	private Boolean secretStatus;
	private String userNickname;
	private LocalDate createdAt;

	public static Page<QnaDto> toQnaPageDto(Page<Qna> qnaList) {
		return qnaList.map(qna -> QnaDto.builder()
			.qnaSeq(qna.getQnaSeq())
			.title(qna.getTitle())
			.content(qna.getContent())
			.answer(qna.getAnswer())
			.secretStatus(qna.getSecretStatus())
			.userNickname(qna.getUser().getUserNickname())
			.createdAt(qna.getCreatedAt().toLocalDate())
			.build());
	}
}