package com.project.luckybocky.qna.dto;

import java.time.LocalDate;
import java.util.Objects;

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
	private Integer authorizedCode;

	public static Page<QnaDto> toQnaPageDto(Page<Qna> qnaList, boolean admin, String key) {
		if (admin) {
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

		return qnaList.map(qna -> QnaDto.builder()
			.qnaSeq(qna.getQnaSeq())
			.title(
				!qna.getSecretStatus() ? qna.getTitle() :
					Objects.equals(qna.getUser().getUserKey(), key) ? qna.getTitle() : "비밀글")
			.content(
				!qna.getSecretStatus() ? qna.getContent() :
					Objects.equals(qna.getUser().getUserKey(), key) ? qna.getContent() : "비밀글")
			.answer(
				!qna.getSecretStatus() ? qna.getAnswer() :
					Objects.equals(qna.getUser().getUserKey(), key) ? qna.getAnswer() : "비밀글")
			.secretStatus(qna.getSecretStatus())
			.userNickname(qna.getUser().getUserNickname())
			.createdAt(qna.getCreatedAt().toLocalDate())
			.build());
	}

	// public static QnaDto toQnaDto(Qna qna, int code) {
	// 	return QnaDto.builder()
	// 		.qnaSeq(qna.getQnaSeq())
	// 		.title(qna.getTitle())
	// 		.content(qna.getContent())
	// 		.answer(qna.getAnswer())
	// 		.secretStatus(qna.getSecretStatus())
	// 		.userNickname(qna.getUser().getUserNickname())
	// 		.createdAt(qna.getCreatedAt().toLocalDate())
	// 		.authorizedCode(code)
	// 		.build();
	// }
}
