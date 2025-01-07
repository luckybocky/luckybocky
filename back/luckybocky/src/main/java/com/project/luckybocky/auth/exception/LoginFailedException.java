package com.project.luckybocky.auth.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"stackTrace", "cause", "localizedMessage", "suppressed"})
public class LoginFailedException extends RuntimeException {
	public LoginFailedException(String message) {
		super(message);
	}
}
