package com.project.luckybocky.pocket.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.luckybocky.common.DataResponseDto;
import com.project.luckybocky.pocket.dto.PocketDto;
import com.project.luckybocky.pocket.exception.PocketNotFoundException;
import com.project.luckybocky.pocket.service.PocketService;
import com.project.luckybocky.user.exception.UserNotFoundException;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
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
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/pocket", produces = "application/json; charset=UTF8")
@Tag(name = "pocket", description = "복주머니 API")
@Slf4j
public class PocketController {
	private final PocketService pocketService;

	@Operation(
		summary = "복주머니 주소로 복주머니 조회",
		description = "복주머니 주소(url)로 복주머니에 포함된 article을 조회한다."
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "복주머니 조회 성공"),
		@ApiResponse(responseCode = "401", description = "사용자 조회 실패",
			content = @Content(schema = @Schema(implementation = UserNotFoundException.class))),
		@ApiResponse(responseCode = "404", description = "복주머니 조회 실패",
			content = @Content(schema = @Schema(implementation = PocketNotFoundException.class)))
	})
	@GetMapping("/{url}")
	public ResponseEntity<DataResponseDto<PocketDto>> getPocket(HttpSession session, @PathVariable String url) {
		PocketDto pocketDto = pocketService.getPocket(url);
		log.debug("복주머니 주소로 복주머니 조회 - url:{}, 복주머니 번호:{}, 복주머니 주인:{}", url, pocketDto.getPocketSeq(),
			pocketDto.getUserNickname());
		return ResponseEntity.status(HttpStatus.OK).body(new DataResponseDto<>("success", pocketDto));
	}

	@Operation(
		summary = "복주머니 주소 조회",
		description = "복주머니 주소(url)를 조회한다."
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "복주머니 주소 조회 성공"),
		@ApiResponse(responseCode = "401", description = "사용자 조회 실패",
			content = @Content(schema = @Schema(implementation = UserNotFoundException.class))),
		@ApiResponse(responseCode = "404", description = "복주머니 조회 실패",
			content = @Content(schema = @Schema(implementation = PocketNotFoundException.class)))
	})
	@GetMapping("/address")
	public ResponseEntity<DataResponseDto> getPocketAddress(HttpSession session) {
		String userKey = (String)session.getAttribute("user");
		String url = pocketService.getPocketAddress(userKey);
		log.debug("복주머니 주소 조회 - 사용자:{}, url:{}", userKey, url);
		return ResponseEntity.status(HttpStatus.OK).body(new DataResponseDto("success", url));
	}

	@Operation(
		summary = "복주머니 생성",
		description = "새로운 복주머니를 생성 후 주소를 가져온다."
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "복주머니 생성 성공"),
		@ApiResponse(responseCode = "401", description = "사용자 조회 실패",
			content = @Content(schema = @Schema(implementation = UserNotFoundException.class)))
	})
	@RateLimiter(name = "saveRateLimiter")
	@PostMapping
	public ResponseEntity<DataResponseDto> createPocket(HttpSession session) {
		String userKey = (String)session.getAttribute("user");
		String url = pocketService.createPocket(userKey);
		log.debug("복주머니 생성 - 사용자:{}, url:{}", userKey, url);
		return ResponseEntity.status(HttpStatus.OK).body(new DataResponseDto("success", url));
	}
}
