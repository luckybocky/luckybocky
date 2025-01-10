package com.project.luckybocky.auth.contorller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.luckybocky.auth.exception.LoginFailedException;
import com.project.luckybocky.common.ResponseDto;
import com.project.luckybocky.auth.service.AuthService;
import com.project.luckybocky.common.SessionNotFoundException;
import com.project.luckybocky.user.dto.UserDto;

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
@RequestMapping(value = "api/v1/auth", produces = "application/json; charset=UTF8")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "auth", description = "로그인/로그아웃 API")
public class AuthController {
	private final AuthService kakaoService;

	@Operation(
		summary = "카카오 로그인",
		description = "사용자가 서비스에 동의하면 카카오 서버에 인증 코드를 받아 callback 호출, 실질적 테스트 불가"
	)
	@GetMapping("/login")
	public ResponseEntity<String> getAuthorizationCode() {
		String authorizationCode = kakaoService.getAuthorizationCode();
		return ResponseEntity.status(HttpStatus.FOUND).header("Location", authorizationCode).build();
	}

	@Operation(
		summary = "카카오 로그인 콜백",
		description = "인증 코드를 통해 넘겨받은 access-code로 서비스 로그인, 실질적 테스트 불가"
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Login success"),
		@ApiResponse(responseCode = "400", description = "Callback Failed, unable to login",
			content = @Content(schema = @Schema(implementation = LoginFailedException.class)))
	})
	@GetMapping("/callback")
	public ResponseEntity<ResponseDto> kakaoLogin(@RequestParam("code") String code, HttpSession session)
		throws JsonProcessingException {
		UserDto userDto = kakaoService.kakaoLogin(code);

		String user = userDto.getUserKey();
		String nickname = userDto.getUserNickname();

		session.setAttribute("user", user);
		session.setAttribute("nickname", nickname);

		log.info("Session user: {}", session.getAttribute("user"));
		log.info("Session nickname: {}", session.getAttribute("nickname"));
		log.info("Login success");

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(new ResponseDto("Login success"));
	}

	@Operation(
		summary = "로그아웃",
		description = "서버 세션 초기화후, 메인페이지로 리다이렉션"
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Logout success"),
		@ApiResponse(responseCode = "401", description = "User session not found, unable to logout",
			content = @Content(schema = @Schema(implementation = SessionNotFoundException.class)))
	})
	@PostMapping("/logout")
	public ResponseEntity<ResponseDto> kakaoLogout(HttpSession session) {
		if(session.getAttribute("user") == null) {
			throw new SessionNotFoundException("User session not found, unable to logout");
		}

		session.invalidate();

		log.info("Logout success");

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(new ResponseDto("Logout success"));
	}
}
