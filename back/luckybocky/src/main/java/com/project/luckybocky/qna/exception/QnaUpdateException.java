package com.project.luckybocky.qna.exception;

import com.project.luckybocky.common.CustomException;

import lombok.Getter;

@Getter
public class QnaUpdateException extends CustomException {
	private static final int statusCode = 503;

	public QnaUpdateException(String log) {
		super(statusCode, log);
	}
}
