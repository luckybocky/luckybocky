package com.project.luckybocky.article.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.luckybocky.article.dto.ArticleResponseDto;
import com.project.luckybocky.article.dto.WriteArticleDto;
import com.project.luckybocky.article.exception.ArticleNotFoundException;
import com.project.luckybocky.article.service.ArticleService;
import com.project.luckybocky.common.DataResponseDto;
import com.project.luckybocky.common.ResponseDto;
import com.project.luckybocky.fortune.exception.FortuneNotFoundException;
import com.project.luckybocky.user.exception.ForbiddenUserException;
import com.project.luckybocky.user.exception.UserNotFoundException;
import com.project.luckybocky.user.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/article")
@RequiredArgsConstructor
@Slf4j
public class ArticleController {
	private final ArticleService articleService;
	private final UserService userService;

	@Operation(
		summary = "복 상세 조회",
		description = "복을 상세조회한다."
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "복 상세 조회 성공"),
		@ApiResponse(responseCode = "404", description = "게시글 조회 실패",
			content = @Content(schema = @Schema(implementation = ArticleNotFoundException.class)))
	})
	@GetMapping
	public ResponseEntity<DataResponseDto<ArticleResponseDto>> getArticleDetails(HttpSession session,
		@RequestParam int articleSeq) {
		String userKey = (String)session.getAttribute("user");
		ArticleResponseDto dto = articleService.getArticleDetails(userKey, articleSeq);
		log.info("복 상세조회 - 번호: {}, 작성자: {}, 내용: {}, 리복: {}, 복 이름: {}", dto.getArticleSeq(), dto.getUserNickname(),
			dto.getArticleContent(), dto.getArticleComment(), dto.getFortuneName());
		return ResponseEntity.status(HttpStatus.OK).body(new DataResponseDto<>("success", dto));
	}

	@Operation(
		summary = "복주머니에 복 달기",
		description = "복주머니에 복을 단다."
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "1. 복 달기 성공 \t\n 2. 복주머니 푸시 알림 성공"),
		@ApiResponse(responseCode = "404", description = "1. 포춘 조회 실패 \t\n 2. 복주머니 조회 실패",
			content = @Content(schema = @Schema(implementation = FortuneNotFoundException.class))),
	})
	@PostMapping
	public ResponseEntity<ResponseDto> writeArticle(HttpSession session,
		@RequestBody WriteArticleDto writeArticleDto) {
		String userKey = (String)session.getAttribute("user");
		articleService.createArticle(userKey, writeArticleDto);

		return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("success"));
	}

	@Operation(
		summary = "복주머니에서 복 삭제",
		description = "복주머니에서 복을 삭제한다."
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "복 삭제 성공"),
		@ApiResponse(responseCode = "401", description = "사용자 조회 실패",
			content = @Content(schema = @Schema(implementation = UserNotFoundException.class))),
		@ApiResponse(responseCode = "403", description = "복주머니 삭제 권한 없음",
			content = @Content(schema = @Schema(implementation = FortuneNotFoundException.class))),
		@ApiResponse(responseCode = "404", description = "게시글 조회 실패",
			content = @Content(schema = @Schema(implementation = ArticleNotFoundException.class)))
	})
	@DeleteMapping
	public ResponseEntity<ResponseDto> deleteArticle(HttpSession session, @RequestParam int articleSeq) {
		String userKey = (String)session.getAttribute("user");

		// 현재 로그인한 사용자가 해당 게시글의 주인(복을 받은 사용자)이 아닐 경우
		if (articleService.getOwnerByArticle(articleSeq) != userService.getUserSeq(userKey)) {
			throw new ForbiddenUserException();
		} else {
			articleService.deleteArticle(articleSeq);
			log.info("복 삭제 - 번호: {}", articleSeq);
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("success"));
		}
	}
}
