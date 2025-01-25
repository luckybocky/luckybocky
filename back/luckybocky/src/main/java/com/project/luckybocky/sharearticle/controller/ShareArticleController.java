package com.project.luckybocky.sharearticle.controller;

import javax.swing.text.html.HTML;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.luckybocky.common.DataResponseDto;
import com.project.luckybocky.common.ResponseDto;
import com.project.luckybocky.sharearticle.dto.ShareArticleDto;
import com.project.luckybocky.sharearticle.dto.ShareArticleSeqDto;
import com.project.luckybocky.sharearticle.dto.WriteShareArticleDto;
import com.project.luckybocky.sharearticle.sevice.ShareArticleService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/v1/share")
@Tag(name = "공유 복", description = "설정, 나의 정보 조회")
public class ShareArticleController {

	private final ShareArticleService shareArticleService;
	//공유 게시글 생성
	@PostMapping
	public ResponseEntity<DataResponseDto<ShareArticleDto>> createShareArticle(HttpSession session,
		@RequestBody WriteShareArticleDto writeShareArticleDto){
		// String userKey = (String)session.getAttribute("user");
		String userKey = "K3858126130";

		ShareArticleDto shareArticle = shareArticleService.createShareArticle(userKey, writeShareArticleDto);

		return ResponseEntity.status(HttpStatus.OK).body(new DataResponseDto<ShareArticleDto>("success", shareArticle));
	}

	//공유게시글을 조회했을때 비회원은 로그인 창으로, 회원은 저장 처리
	@PostMapping("/save")
	public ResponseEntity<DataResponseDto<Boolean>> createShareArticle(HttpSession session, @RequestBody
		ShareArticleSeqDto shareArticleSeqDto){
		String userKey = "K3858126130";
		// String userKey = (String)session.getAttribute("user");

		boolean isLogin = false;
		if(userKey != null){
			isLogin = true;
			int shareArticleSeq = shareArticleSeqDto.getShareArticleSeq();
			shareArticleService.isMyShareArticle(userKey,shareArticleSeq);
		}
		return ResponseEntity.status(HttpStatus.OK).body(new DataResponseDto<>("success", isLogin));
	}






}
