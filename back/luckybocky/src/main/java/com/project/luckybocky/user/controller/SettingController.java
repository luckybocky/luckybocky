package com.project.luckybocky.user.controller;

import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.luckybocky.common.DataResponseDto;
import com.project.luckybocky.common.ResponseDto;
import com.project.luckybocky.user.dto.FirebaseKeyRequest;
import com.project.luckybocky.user.dto.SettingDto;
import com.project.luckybocky.user.dto.UserLoginDto;
import com.project.luckybocky.user.exception.UserNotFoundException;
import com.project.luckybocky.user.service.UserSettingService;

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

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
@Tag(name = "사용자 정보", description = "설정, 나의 정보 조회")
public class SettingController {
	private final UserSettingService userSettingService;

	@Description("설정변경(푸시, 공개유무,닉네임)")
	@Operation(
		summary = "설정변경(푸시, 공개유무,닉네임)",
		description = "FCM에서 발급받은 푸시 키를 업데이트한다."
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "FCM키 업데이트 성공"),
		@ApiResponse(responseCode = "401", description = "사용자를 찾을 수 없음",
			content = @Content(schema = @Schema(implementation = UserNotFoundException.class)))
	})
	@PutMapping("/user")
	public ResponseEntity<ResponseDto> updateSetting(@RequestBody SettingDto settingDto, HttpSession session) {
		String userKey = (String)session.getAttribute("user");

		userSettingService.updateUserSetting(userKey, settingDto.getUserNickname(), settingDto.getAlarmStatus(),
			settingDto.getFortuneVisibility());

		log.info("사용자 정보 업데이트 {}", userKey);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("success"));
	}

	@Description("내 정보 조회")
	@Operation(
		summary = "내 정보 조회",
		description = "닉네임, 알림여부, 복공개 여부, 가입일을 조회한다"
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "1. 사용자 조회 성공"),
		@ApiResponse(responseCode = "401", description = "1. 사용자를 찾을 수 없음",
			content = @Content(schema = @Schema(implementation = UserNotFoundException.class)))
	})
	@GetMapping("/user")
	public ResponseEntity<DataResponseDto<UserLoginDto>> loadUserInfo(HttpSession session) {
		String userKey = (String)session.getAttribute("user");

		UserLoginDto userLoginDto = userSettingService.getUserLogin(userKey);
		return ResponseEntity.status(HttpStatus.OK).body(new DataResponseDto<>("success", userLoginDto));
	}

	@Description("firebase Key 업데이트")
	@Operation(
		summary = "firebase Key 업데이트 요청",
		description = "FCM에서 발급받은 푸시 키를 업데이트한다."
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "1. 파이어베이스 키 업데이트 성공"),
		@ApiResponse(responseCode = "401", description = "2. 사용자를 찾을 수 없음",
			content = @Content(schema = @Schema(implementation = UserNotFoundException.class)))
	})
	@RateLimiter(name = "saveRateLimiter")
	@PutMapping("/firebase")
	public ResponseEntity<ResponseDto> updateFireBaseKey(HttpSession session,
		@RequestBody FirebaseKeyRequest firebaseKeyRequest) {
		String userKey = (String)session.getAttribute("user");

		String firebaseKey = firebaseKeyRequest.getFirebaseKey();

		userSettingService.updateFireBaseKey(userKey, firebaseKey);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("success"));

	}
}
