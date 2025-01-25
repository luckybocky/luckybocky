package com.project.luckybocky.qna.exception;

import com.project.luckybocky.common.CustomException;

import lombok.Getter;

@Getter
public class QnaSaveException extends CustomException {
	private static final int statusCode = 503;

	public QnaSaveException(String log) {
		super(statusCode, log);
	}
}
