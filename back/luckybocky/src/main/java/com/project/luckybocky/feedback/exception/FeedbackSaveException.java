package com.project.luckybocky.feedback.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"stackTrace", "cause", "localizedMessage", "suppressed"})
public class FeedbackSaveException extends RuntimeException {
	public FeedbackSaveException(String message) {
		super(message);
	}
}
