package com.project.luckybocky.qna.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class QnaUserReqDto {
	private String title;
	private String content;
	private Boolean secretStatus;
}
