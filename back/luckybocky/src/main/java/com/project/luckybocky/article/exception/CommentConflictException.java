package com.project.luckybocky.article.exception;

import com.project.luckybocky.exam.exception.CustomException;

public class CommentConflictException extends CustomException {
	private final int statusCode = 409;
	private final String message = "Already exist Comment";
}
