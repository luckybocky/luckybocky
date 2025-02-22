package com.project.luckybocky.admin.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.luckybocky.admin.dto.ArticleAdminListResDto;
import com.project.luckybocky.admin.dto.FeedbackAdminListResDto;
import com.project.luckybocky.admin.dto.QnaAdminListResDto;
import com.project.luckybocky.admin.dto.QnaAdminReqDto;
import com.project.luckybocky.admin.dto.ReportAdminListResDto;
import com.project.luckybocky.admin.dto.UserAdminListResDto;
import com.project.luckybocky.admin.service.AdminService;
import com.project.luckybocky.auth.exception.AuthErrorException;
import com.project.luckybocky.common.DataResponseDto;
import com.project.luckybocky.common.ResponseDto;
import com.project.luckybocky.qna.exception.QnaSaveException;
import com.project.luckybocky.user.dto.UserDto;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "관리자 화면", description = "데이터 처리 API")
@RequestMapping(value = "api/v1/backoffice", produces = "application/json; charset=UTF8")
public class AdminController {
	private final AdminService adminService;

	public Pageable createPageable(int page, int size, boolean desc) {
		return PageRequest.of(page, size, desc ?
			Sort.by("createdAt").descending() :
			Sort.by("createdAt").ascending());
	}

	@Operation(
		summary = "권한 확인",
		description = "백오피스 접근을 위한 권한을 확인한다."
	)
	@GetMapping()  // TODO 관리자 계정 로그인 -> 구현 방식에 대해 생각
	public ResponseEntity<DataResponseDto<UserDto>> checkAuth(HttpSession session) {
		return null;
	}

	@Operation(
		summary = "유저 정보 조회",
		description = "필터링에 따른 유저 정보들을 조회한다."
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "유저 목록 조회 성공"),
		@ApiResponse(responseCode = "403", description = "권한 없음",
			content = @Content(schema = @Schema(implementation = AuthErrorException.class)))
	})
	@GetMapping("/users")
	public ResponseEntity<DataResponseDto<UserAdminListResDto>> getUsers(
		@RequestParam(value = "page", defaultValue = "0") @Schema(description = "표시할 페이지", type = "int") int page,
		@RequestParam(value = "size", defaultValue = "5") @Schema(description = "페이지당 데이터 수", type = "int") int size,
		@RequestParam(value = "start-date", required = false) @Schema(description = "시작 날짜(YYYY-MM-DD)") String startDate,
		@RequestParam(value = "end-date", required = false) @Schema(description = "종료 날짜(YYYY-MM-DD)") String endDate,
		@RequestParam(value = "desc", required = false) @Schema(description = "생성일 기준 내림차순 여부(지정하지 않으면 오름차순(false))", type = "boolean") boolean desc,
		@RequestParam(value = "user-nickname", required = false) @Schema(description = "닉네임 여부(지정하지 않으면 모두 포함)", type = "Boolean") Boolean userNickname
	) {
		Pageable pageable = createPageable(page, size, desc);

		UserAdminListResDto userAdminListResDto = adminService.getFilteredUsers(startDate, endDate, userNickname,
			pageable);

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(new DataResponseDto<>("유저 목록 조회 완료", userAdminListResDto));
	}

	@Operation(
		summary = "피드백 정보 조회",
		description = "필터링에 따른 피드백 정보들을 조회한다."
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "피드백 목록 조회 성공"),
		@ApiResponse(responseCode = "403", description = "권한 없음",
			content = @Content(schema = @Schema(implementation = AuthErrorException.class)))
	})
	@GetMapping("/feedbacks")
	public ResponseEntity<DataResponseDto<FeedbackAdminListResDto>> getFeedbacks(
		@RequestParam(value = "page", defaultValue = "0") @Schema(description = "표시할 페이지", type = "int") int page,
		@RequestParam(value = "size", defaultValue = "5") @Schema(description = "페이지당 데이터 수", type = "int") int size,
		@RequestParam(value = "start-date", required = false) @Schema(description = "시작 날짜(YYYY-MM-DD)") String startDate,
		@RequestParam(value = "end-date", required = false) @Schema(description = "종료 날짜(YYYY-MM-DD)") String endDate,
		@RequestParam(value = "desc", required = false) @Schema(description = "생성일 기준 내림차순 여부(지정하지 않으면 오름차순(false))", type = "boolean") boolean desc
	) {
		Pageable pageable = createPageable(page, size, desc);

		FeedbackAdminListResDto feedbackAdminListResDto = adminService.getFilteredFeedbacks(startDate, endDate,
			pageable);

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(new DataResponseDto<>("피드백 목록 조회 완료", feedbackAdminListResDto));
	}

	@Operation(
		summary = "신고 정보 조회",
		description = "필터링에 따른 신고 정보들을 조회한다."
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "신고 목록 조회 성공"),
		@ApiResponse(responseCode = "403", description = "권한 없음",
			content = @Content(schema = @Schema(implementation = AuthErrorException.class)))
	})
	@GetMapping("/reports")
	public ResponseEntity<DataResponseDto<ReportAdminListResDto>> getReports(
		@RequestParam(value = "page", defaultValue = "0") @Schema(description = "표시할 페이지", type = "int") int page,
		@RequestParam(value = "size", defaultValue = "5") @Schema(description = "페이지당 데이터 수", type = "int") int size,
		@RequestParam(value = "start-date", required = false) @Schema(description = "시작 날짜(YYYY-MM-DD)") String startDate,
		@RequestParam(value = "end-date", required = false) @Schema(description = "종료 날짜(YYYY-MM-DD)") String endDate,
		@RequestParam(value = "desc", required = false) @Schema(description = "생성일 기준 내림차순 여부(지정하지 않으면 오름차순(false))", type = "boolean") boolean desc
	) {
		Pageable pageable = createPageable(page, size, desc);

		ReportAdminListResDto reportAdminListResDto = adminService.getFilteredReports(startDate, endDate,
			pageable);

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(new DataResponseDto<>("신고 목록 조회 완료", reportAdminListResDto));
	}

	@Operation(
		summary = "문의 정보 조회",
		description = "필터링에 따른 문의 정보들을 조회한다."
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "문의 목록 조회 성공"),
		@ApiResponse(responseCode = "403", description = "권한 없음",
			content = @Content(schema = @Schema(implementation = AuthErrorException.class)))
	})
	@GetMapping("/qnas")
	public ResponseEntity<DataResponseDto<QnaAdminListResDto>> getQnas(
		@RequestParam(value = "page", defaultValue = "0") @Schema(description = "표시할 페이지", type = "int") int page,
		@RequestParam(value = "size", defaultValue = "5") @Schema(description = "페이지당 데이터 수", type = "int") int size,
		@RequestParam(value = "start-date", required = false) @Schema(description = "시작 날짜(YYYY-MM-DD)") String startDate,
		@RequestParam(value = "end-date", required = false) @Schema(description = "종료 날짜(YYYY-MM-DD)") String endDate,
		@RequestParam(value = "deleted", required = false) @Schema(description = "삭제 여부(지정하지 않으면 모두 포함 )", type = "Boolean") Boolean deleted,
		@RequestParam(value = "desc", required = false) @Schema(description = "생성일 기준 내림차순 여부(지정하지 않으면 오름차순(false))", type = "boolean") boolean desc,
		@RequestParam(value = "answer", required = false) @Schema(description = "답변 여부(지정하지 않으면 모두 포함)", type = "Boolean") Boolean answer
	) {
		Pageable pageable = createPageable(page, size, desc);

		QnaAdminListResDto qnaAdminListResDto = adminService.getFilteredQnas(startDate, endDate, deleted,
			answer, pageable);

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(new DataResponseDto<>("문의 목록 조회 완료", qnaAdminListResDto));
	}

	@RateLimiter(name = "saveRateLimiter")
	@Operation(
		summary = "답변 등록",
		description = "질문에 대한 답변을 등록한다."
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "답변 등록 성공"),
		@ApiResponse(responseCode = "503", description = "서버 문제로 인해 답변 등록 실패",
			content = @Content(schema = @Schema(implementation = QnaSaveException.class))),
		@ApiResponse(responseCode = "403", description = "권한 없음",
			content = @Content(schema = @Schema(implementation = AuthErrorException.class)))
	})
	@PutMapping("/qnas/{qnaSeq}/answer")
	public ResponseEntity<ResponseDto> addAnswer(@PathVariable Integer qnaSeq,
		@RequestBody QnaAdminReqDto answerReqDto) {
		adminService.putAnsweredQna(qnaSeq, answerReqDto);
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(new ResponseDto(qnaSeq + "번 문의 답변 등록 성공"));
	}

	@Operation(
		summary = "게시글 정보 조회",
		description = "필터링에 따른 게시글 정보들을 조회한다."
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "게시글 목록 조회 성공"),
		@ApiResponse(responseCode = "403", description = "권한 없음",
			content = @Content(schema = @Schema(implementation = AuthErrorException.class)))
	})
	@GetMapping("/articles")
	public ResponseEntity<DataResponseDto<ArticleAdminListResDto>> getArticles(
		@RequestParam(value = "page", defaultValue = "0") @Schema(description = "표시할 페이지", type = "int") int page,
		@RequestParam(value = "size", defaultValue = "5") @Schema(description = "페이지당 데이터 수", type = "int") int size,
		@RequestParam(value = "start-date", required = false) @Schema(description = "시작 날짜(YYYY-MM-DD)") String startDate,
		@RequestParam(value = "end-date", required = false) @Schema(description = "종료 날짜(YYYY-MM-DD)") String endDate,
		@RequestParam(value = "deleted", required = false) @Schema(description = "삭제 여부(지정하지 않으면 모두 포함)", type = "Boolean") Boolean deleted,
		@RequestParam(value = "desc", required = false) @Schema(description = "생성일 기준 내림차순 여부(지정하지 않으면 오름차순(false))", type = "boolean") boolean desc
	) {
		Pageable pageable = createPageable(page, size, desc);

		ArticleAdminListResDto articleAdminListResDto = adminService.getFilteredArticles(startDate,
			endDate, deleted, pageable);

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(new DataResponseDto<>("게시글 목록 조회 완료", articleAdminListResDto));
	}
}
