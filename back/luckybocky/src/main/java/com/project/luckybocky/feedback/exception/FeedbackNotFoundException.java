package com.project.luckybocky.feedback.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"stackTrace", "cause", "localizedMessage", "suppressed"})
public class FeedbackNotFoundException extends RuntimeException {
	public FeedbackNotFoundException(String message) {
		super(message);
	}
}
