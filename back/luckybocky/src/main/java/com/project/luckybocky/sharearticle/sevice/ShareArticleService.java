package com.project.luckybocky.sharearticle.sevice;

import com.project.luckybocky.sharearticle.dto.ShareArticleDto;
import com.project.luckybocky.sharearticle.dto.WriteShareArticleDto;

public interface ShareArticleService {
	//공유 게시글 생성
	ShareArticleDto createShareArticle(String userKey, WriteShareArticleDto writeShareArticleDto);

	//공유 게시글 조회
	void enterShareArticle(String userKey, int shareArticleSeq);


	//본인 공유게시글인지 판단(본인의 공유게시글은 저장하지 않아야 하기 떄문에)
	boolean isMyShareArticle(String userKey, int shareArticleSeq);


	//저장하려는 유저가 이미 이 공유게시글을 저장한 경우
	boolean isExistsShareArticle(String userKey, int shareArticleSeq);

}
