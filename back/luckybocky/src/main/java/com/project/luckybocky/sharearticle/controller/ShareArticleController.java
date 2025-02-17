package com.project.luckybocky.sharearticle.controller;

import java.util.List;

import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.luckybocky.common.DataResponseDto;
import com.project.luckybocky.fortune.exception.FortuneNotFoundException;
import com.project.luckybocky.sharearticle.dto.ShareArticleDto;
import com.project.luckybocky.sharearticle.dto.ShareArticleLoginDto;
import com.project.luckybocky.sharearticle.dto.WriteShareArticleDto;
import com.project.luckybocky.sharearticle.exception.ShareArticleException;
import com.project.luckybocky.sharearticle.sevice.ShareArticleService;
import com.project.luckybocky.user.exception.UserNotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

	@Description("공유하고자 하는 게시글을 생성한다.")
	@Operation(
		summary = "공유 게시글 생성",
		description = "공유게시글을 생성하고 공유할수있는 주소를 반환한다."
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "1. 공유게시글 생성 성공"),
		@ApiResponse(responseCode = "401", description = "1. 사용자를 찾을 수 없음",
			content = @Content(schema = @Schema(implementation = UserNotFoundException.class))),
		@ApiResponse(responseCode = "404", description = "1. 포춘을 찾을 수 없음",
			content = @Content(schema = @Schema(implementation = FortuneNotFoundException.class)))
	})
	@PostMapping
	public ResponseEntity<DataResponseDto<ShareArticleDto>> createShareArticle(HttpSession session,
		@RequestBody WriteShareArticleDto writeShareArticleDto) {
		String userKey = (String)session.getAttribute("user");

		ShareArticleDto shareArticle = shareArticleService.createShareArticle(userKey, writeShareArticleDto);

		return ResponseEntity.status(HttpStatus.OK).body(new DataResponseDto<ShareArticleDto>("success", shareArticle));
	}

	@Description("공유게시글을 조회했을때 비회원은 로그인 창으로, 회원은 저장 처리")
	@Operation(
		summary = "공유게시글 조회, 저장",
		description = "공유게시글을 조회했을때 비회원은 로그인 창으로, 회원은 저장 처리"
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "1. 공유게시글 생성 성공"),
		@ApiResponse(responseCode = "404", description = "1. 공유게시글을 찾을 수 없음",
			content = @Content(schema = @Schema(implementation = ShareArticleException.class)))
	})
	@GetMapping("/{address}")
	public ResponseEntity<DataResponseDto<ShareArticleLoginDto>> enterShareArticle(HttpSession session,
		@PathVariable String address) {
		String userKey = (String)session.getAttribute("user");

		if (userKey == null) {
			log.info("비회원의 공유게시글 찾기입니다.");
			ShareArticleDto shareArticle = shareArticleService.findShareArticle(address);
			ShareArticleLoginDto shareArticleLoginDto = new ShareArticleLoginDto(false, shareArticle);
			return ResponseEntity.status(HttpStatus.OK).body(new DataResponseDto<>("success", shareArticleLoginDto));
		} else {
			log.info("회원의 공유게시글 찾기입니다. {}", userKey);
			ShareArticleDto shareArticleDto = shareArticleService.enterShareArticle(userKey, address);
			ShareArticleLoginDto shareArticleLoginDto = new ShareArticleLoginDto(true, shareArticleDto);
			return ResponseEntity.status(HttpStatus.OK).body(new DataResponseDto<>("success", shareArticleLoginDto));
		}

	}

	@Description("내가 공유한 게시글과, 다른사람들이 저장한 횟수")
	@Operation(
		summary = "공유게시글 현황 조회",
		description = "내가 공유한 게시글과 다른사람들이 저장한 횟수를 조회한다."
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "1. 나의 공유게시글 조회 성공"),
		@ApiResponse(responseCode = "401", description = "1. 사용자를 찾을 수 없음",
			content = @Content(schema = @Schema(implementation = UserNotFoundException.class)))
	})
	@GetMapping
	public ResponseEntity<DataResponseDto<List<ShareArticleDto>>> findMyShareArticle(HttpSession session) {
		String userKey = (String)session.getAttribute("user");

		List<ShareArticleDto> myShareArticle = shareArticleService.getMyShareArticle(userKey);

		return ResponseEntity.status(HttpStatus.OK).body(new DataResponseDto<>("success", myShareArticle));

	}

}
