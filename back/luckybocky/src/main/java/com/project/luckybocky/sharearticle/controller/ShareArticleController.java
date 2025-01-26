package com.project.luckybocky.sharearticle.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.luckybocky.common.DataResponseDto;
import com.project.luckybocky.sharearticle.dto.ShareArticleAddressDto;
import com.project.luckybocky.sharearticle.dto.ShareArticleDto;
import com.project.luckybocky.sharearticle.dto.ShareArticleLoginDto;
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
		@RequestBody WriteShareArticleDto writeShareArticleDto) {
		String userKey = (String)session.getAttribute("user");
		// String userKey = "K3858126130";
x
		ShareArticleDto shareArticle = shareArticleService.createShareArticle(userKey, writeShareArticleDto);

		return ResponseEntity.status(HttpStatus.OK).body(new DataResponseDto<ShareArticleDto>("success", shareArticle));
	}

	//공유게시글을 조회했을때 비회원은 로그인 창으로, 회원은 저장 처리
	@PostMapping("/save")
	public ResponseEntity<DataResponseDto<ShareArticleLoginDto>> enterShareArticle(HttpSession session, @RequestBody
	ShareArticleAddressDto shareArticleAddressDto) {
		// String userKey = "K3858126130";
		String userKey = (String)session.getAttribute("user");
		String shareArticleAddress = shareArticleAddressDto.getShareArticleAddress();

		if (userKey == null) {
			log.info("비회원의 공유게시글 찾기입니다.");

			ShareArticleDto shareArticle = shareArticleService.findShareArticle(shareArticleAddress);
			ShareArticleLoginDto shareArticleLoginDto = new ShareArticleLoginDto(false, shareArticle);
			return ResponseEntity.status(HttpStatus.OK).body(new DataResponseDto<>("success", shareArticleLoginDto));
		} else {
			log.info("회원의 공유게시글 찾기입니다. {}", userKey);

			ShareArticleDto shareArticleDto = shareArticleService.enterShareArticle(userKey, shareArticleAddress);
			ShareArticleLoginDto shareArticleLoginDto = new ShareArticleLoginDto(true, shareArticleDto);
			return ResponseEntity.status(HttpStatus.OK).body(new DataResponseDto<>("success", shareArticleLoginDto));
		}

	}

}
