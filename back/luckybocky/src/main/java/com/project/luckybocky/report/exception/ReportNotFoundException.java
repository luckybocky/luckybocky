package com.project.luckybocky.report.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"stackTrace", "cause", "localizedMessage", "suppressed"})
public class ReportNotFoundException extends RuntimeException {
	public ReportNotFoundException(String message) {
		super(message);
	}
}
