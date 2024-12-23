package com.project.luckybocky.login.contorller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.luckybocky.login.service.LoginService;
import com.project.luckybocky.user.dto.UserDto;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class LoginController {
	private final LoginService loginService;

	@GetMapping("/login")
	public ResponseEntity<String> getAuthorizationCode() {
		String authorizationCode = loginService.getAuthorizationCode();
		return ResponseEntity.status(HttpStatus.FOUND).header("Location", authorizationCode).build();
	}

	@GetMapping("/callback")
	public ResponseEntity<Map<String, String>> kakaoLogin(@RequestParam("code") String code, HttpSession session)
		throws JsonProcessingException {
		UserDto userDto = loginService.kakaoLogin(code);

		if(userDto != null) {
			String user = userDto.getUserKey();
			String nickname = userDto.getUserNickname();
			Map<String, String> response = new HashMap<>();
			session.setAttribute("user", user);
			session.setAttribute("nickname", nickname);
			response.put("user", user);
			response.put("nickname", nickname);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}
}
