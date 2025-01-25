package com.project.luckybocky.sharearticle.exception;

import com.project.luckybocky.common.CustomException;

import lombok.Getter;

@Getter
public class ShareArticleException extends CustomException {
	private static final int statusCode = 404;
	private static final String message = "공유 게시글을 찾을 수 없습니다.";
	public ShareArticleException(){
		super(statusCode,message);
	}

}
