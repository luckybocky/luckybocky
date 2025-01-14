package com.project.luckybocky.fortune.exception;

import com.project.luckybocky.exam.exception.CustomException;

public class FortuneNotFoundException extends CustomException {
	private final int statusCode = 404;
	private final String message = "Not found fortune";
}
