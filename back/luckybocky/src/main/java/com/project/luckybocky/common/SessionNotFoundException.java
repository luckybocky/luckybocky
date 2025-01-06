package com.project.luckybocky.common;

public class SessionNotFoundException extends RuntimeException {
	public SessionNotFoundException(String message) {
		super(message);
	}
}
