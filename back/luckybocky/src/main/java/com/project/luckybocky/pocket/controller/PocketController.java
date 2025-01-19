package com.project.luckybocky.pocket.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.luckybocky.article.service.ArticleService;
import com.project.luckybocky.common.DataResponseDto;
import com.project.luckybocky.pocket.dto.PocketDto;
import com.project.luckybocky.pocket.dto.PocketInfoDto;
import com.project.luckybocky.pocket.exception.PocketNotFoundException;
import com.project.luckybocky.pocket.service.PocketService;
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
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/pocket", produces = "application/json; charset=UTF8")
@Tag(name = "pocket", description = "복주머니 API")
@Slf4j
public class PocketController {
	private final PocketService pocketService;
	private final ArticleService articleService;

	//    @Description("나의 복주머니 조회")
	//    @GetMapping
	//    public ResponseEntity<DataResponseDto<PocketDto>> getAllArticlesInPocket(HttpSession session){
	//        String userKey = (String) session.getAttribute("user");
	//        PocketInfoDto pocketInfoDto = pocketService.getPocketInfoByUser(userKey);
	//
	//        PocketDto pocketDto = new PocketDto();
	//        pocketDto.setPocketSeq(pocketInfoDto.getPocketSeq());
	//        pocketDto.setUserNickname(pocketInfoDto.getUserNickname());
	//        pocketDto.setArticles(articleService.getArticlesByUser(userKey));
	//
	//        return ResponseEntity.status(HttpStatus.OK).body(new DataResponseDto<>("success: getting pocket by user", pocketDto));
	//    }

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
		PocketDto pocketDto = new PocketDto();
		PocketInfoDto findPocket = pocketService.getPocketInfo(url);
		pocketDto.setPocketSeq(findPocket.getPocketSeq());
		pocketDto.setUserNickname(findPocket.getUserNickname());

		//        if (userKey == findPocket.getUserKey()){    // 복주머니 주인의 경우 (모든 게시글을 볼 수 있어야 함)
		pocketDto.setArticles(articleService.getAllArticles(findPocket.getPocketSeq()));
		//        } else {        // 복주머니의 주인이 아닐경우
		//            if (!findPocket.isFortuneVisibility()){     // 비공개 복주머니일 경우 -> 전부 비공개로 설정
		//                pocketDto.setArticles(articleService.getAllArticlesInvisible(findPocket.getPocketSeq()));
		//            } else {    // 비공개 복주머니가 아닐 경우 -> 비공개 글만 비공개로 설정
		//                pocketDto.setArticles(articleService.getAllArticlesCheck(findPocket.getPocketSeq()));
		//            }
		//        }

		log.info("주소(url)로 복주머니 조회 - 주소: {}, 번호: {}, 복주머니 주인: {}", url, pocketDto.getPocketSeq(),
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
		String address = pocketService.getPocketAddress(userKey);
		log.info("복주머니 주소(url) 조회 - 주소: {}", address);
		return ResponseEntity.status(HttpStatus.OK).body(new DataResponseDto("success", address));
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
	@PostMapping
	public ResponseEntity<DataResponseDto> createPocket(HttpSession session) {
		String userKey = (String)session.getAttribute("user");
		String address = pocketService.createPocket(userKey);
		log.info("복주머니 생성 - 주소: {}", address);
		return ResponseEntity.status(HttpStatus.OK).body(new DataResponseDto("success", address));
	}
}
