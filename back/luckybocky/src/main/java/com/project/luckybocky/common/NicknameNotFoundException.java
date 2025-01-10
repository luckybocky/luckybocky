package com.project.luckybocky.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"stackTrace", "cause", "localizedMessage", "suppressed"})
public class NicknameNotFoundException extends RuntimeException {
	public NicknameNotFoundException(String message) {
		super(message);
	}
}
