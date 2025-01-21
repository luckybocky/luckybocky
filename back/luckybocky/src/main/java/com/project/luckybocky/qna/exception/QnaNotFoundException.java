package com.project.luckybocky.qna.exception;

import com.project.luckybocky.common.CustomException;

import lombok.Getter;

@Getter
public class QnaNotFoundException extends CustomException {
	private static final int statusCode = 404;
	private static final String message = "QnA를 찾을 수 없습니다.";

	public QnaNotFoundException() {
		super(statusCode, message);
	}
}
