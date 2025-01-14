package com.project.luckybocky.pocket.exception;

import com.project.luckybocky.common.exception.CustomException;

public class PocketNotFoundException extends CustomException {
	private final int statusCode = 404;
	private final String message = "Not found pocket";
}
