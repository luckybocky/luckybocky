package com.project.luckybocky.qna.exception;

import com.project.luckybocky.common.CustomException;

import lombok.Getter;

@Getter
public class QnaSaveException extends CustomException {
	private static final int statusCode = 500;  // 협의 필요
	private static final String message = "저장에 문제가 발생했습니다: ";

	public QnaSaveException(String log) {
		super(statusCode, message + log);
	}
}
