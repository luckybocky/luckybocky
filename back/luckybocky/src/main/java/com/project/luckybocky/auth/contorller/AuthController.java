package com.project.luckybocky.auth.contorller;

import org.springframework.context.annotation.Description;
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
import com.project.luckybocky.user.repository.UserRepository;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
	private final AuthService kakaoService;
	private final UserRepository userRepository;

	@Description("로그인")
	@GetMapping("/login")
	public ResponseEntity<String> getAuthorizationCode() {
		String authorizationCode = kakaoService.getAuthorizationCode();
		return ResponseEntity.status(HttpStatus.FOUND).header("Location", authorizationCode).build();
	}

	@GetMapping("/callback")
	public ResponseEntity<ResponseDto> kakaoLogin(@RequestParam("code") String code, HttpSession session)
		throws JsonProcessingException {
		UserDto userDto = kakaoService.kakaoLogin(code);
		log.debug("{}", userDto);
		if(userDto == null) {
			throw new LoginFailedException("Callback Failed, unable to login");
		}

		String user = userDto.getUserKey();
		String nickname = userDto.getUserNickname();

		session.setAttribute("user", user);
		session.setAttribute("nickname", nickname);

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(new ResponseDto("Login success"));
	}

	@Description("로그아웃")
	@PostMapping("/logout")
	public ResponseEntity<ResponseDto> kakaoLogout(HttpSession session) {
		if(session.getAttribute("user") == null) {
			throw new SessionNotFoundException("User session not found, unable to logout");
		}

		session.invalidate();

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(new ResponseDto("Logout success"));
	}
}
