package com.project.luckybocky.article.controller;

import org.springframework.context.annotation.Description;
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
import com.project.luckybocky.article.service.ArticleService;
import com.project.luckybocky.common.DataResponseDto;
import com.project.luckybocky.common.ResponseDto;
import com.project.luckybocky.user.exception.ForbiddenUserException;
import com.project.luckybocky.user.service.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/article")
@RequiredArgsConstructor
public class ArticleController {
	private final ArticleService articleService;
	private final UserService userService;

	@Description("복 상세 조회")
	@GetMapping
	public ResponseEntity<DataResponseDto<ArticleResponseDto>> getArticleDetails(HttpSession session,
		@RequestParam int articleSeq) {
		String userKey = (String)session.getAttribute("user");
		ArticleResponseDto articleResponseDto = articleService.getArticleDetails(userKey, articleSeq);
		return ResponseEntity.status(HttpStatus.OK).body(new DataResponseDto<>("success", articleResponseDto));
	}

	@Description("복주머니에 복 달기")
	@PostMapping
	public ResponseEntity<ResponseDto> writeArticle(HttpSession session, @RequestBody WriteArticleDto writeArticleDto) {
		String userKey = (String)session.getAttribute("user");
		articleService.createArticle(userKey, writeArticleDto);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("success"));
	}

	@Description("복주머니에서 복 삭제")
	@DeleteMapping
	public ResponseEntity<ResponseDto> deleteArticle(HttpSession session, @RequestParam int articleSeq) {
		String userKey = (String)session.getAttribute("user");

		// 현재 로그인한 사용자가 해당 게시글의 주인(복을 받은 사용자)이 아닐 경우
		if (articleService.getOwnerByArticle(articleSeq) != userService.getUserSeq(userKey)) {
			throw new ForbiddenUserException();
		} else {
			articleService.deleteArticle(articleSeq);
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("success"));
		}
	}
}
