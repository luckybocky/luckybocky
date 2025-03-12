package com.project.luckybocky.auth.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"stackTrace", "cause", "localizedMessage", "suppressed"})
public class AuthErrorException extends RuntimeException {
	public AuthErrorException(String message) {
		super(message);
	}
}
