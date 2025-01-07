package com.project.luckybocky.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"stackTrace", "cause", "localizedMessage", "suppressed"})
public class SessionNotFoundException extends RuntimeException {
	public SessionNotFoundException(String message) {
		super(message);
	}
}
