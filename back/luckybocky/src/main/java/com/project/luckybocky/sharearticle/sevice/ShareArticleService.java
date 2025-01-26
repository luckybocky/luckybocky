package com.project.luckybocky.sharearticle.sevice;

import java.util.List;

import com.project.luckybocky.sharearticle.dto.ShareArticleDto;
import com.project.luckybocky.sharearticle.dto.WriteShareArticleDto;
import com.project.luckybocky.sharearticle.entity.ShareArticle;

public interface ShareArticleService {
	//공유 게시글 생성
	ShareArticleDto createShareArticle(String userKey, WriteShareArticleDto writeShareArticleDto);

	//공유 게시글을 저장하기
	ShareArticleDto enterShareArticle(String userKey, String shareArticleAddress);

	//공유게시글을 uuid로 찾기
	ShareArticleDto findShareArticle(String shareArticleAddress);


	//본인 공유게시글인지 판단(본인의 공유게시글은 저장하지 않아야 하기 떄문에)
	boolean isMyShareArticle(String userKey, ShareArticle shareArticle);


	//저장하려는 유저가 이미 이 공유게시글을 저장한 경우
	boolean isExistsShareArticle(String userKey, ShareArticle shareArticle);

	//본인이 생성한 공유게시글과 몇번 사람들이 저장(공유)했는지 반환
	List<ShareArticleDto> getMyShareArticle(String userKey);

}
