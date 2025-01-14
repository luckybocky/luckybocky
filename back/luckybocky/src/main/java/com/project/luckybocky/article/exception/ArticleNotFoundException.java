package com.project.luckybocky.article.exception;

import com.project.luckybocky.exam.exception.CustomException;

public class ArticleNotFoundException extends CustomException {
	private final int statusCode = 404;
	private final String message = "Not found article";
}
