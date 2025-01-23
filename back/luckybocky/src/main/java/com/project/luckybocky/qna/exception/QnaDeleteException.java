package com.project.luckybocky.qna.exception;

import com.project.luckybocky.common.CustomException;

import lombok.Getter;

@Getter
public class QnaDeleteException extends CustomException {
	private static final int statusCode = 503;

	public QnaDeleteException(String log) {
		super(statusCode, log);
	}
}
