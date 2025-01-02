package com.project.luckybocky.kakao.contorller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.luckybocky.common.Message;
import com.project.luckybocky.kakao.service.KakaoService;
import com.project.luckybocky.user.dto.UserDto;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class KakaoController {
	private final KakaoService kakaoService;

	@GetMapping("/login")
	public ResponseEntity<String> getAuthorizationCode() {
		String authorizationCode = kakaoService.getAuthorizationCode();
		return ResponseEntity.status(HttpStatus.FOUND).header("Location", authorizationCode).build();
	}

	@GetMapping("/callback")
	public ResponseEntity<Message> kakaoLogin(@RequestParam("code") String code, HttpSession session)
		throws JsonProcessingException {
		/*
		TODO 20250103 조우재 작성
		- 세션이 있는지 확인하는 코드(이미 로그인이 되어 있는 확인하는 코드) 작성할 것
		- 세션을 파라미터로 받을 필요가 있는지 체크할 것
		 */
		UserDto userDto = kakaoService.kakaoLogin(code);

		if(userDto != null) {
			String user = userDto.getUserKey();
			String nickname = userDto.getUserNickname();
			Map<String, String> response = new HashMap<>();
			session.setAttribute("user", user);
			session.setAttribute("nickname", nickname);
			response.put("user", user);
			response.put("nickname", nickname);

			return ResponseEntity
				.status(HttpStatus.OK)
				.body(new Message(HttpStatus.OK, "로그인 성공", response));
		}
		return ResponseEntity
			.status(HttpStatus.NOT_FOUND)
			.body(new Message(HttpStatus.NOT_FOUND, "로그인 실패", null));
	}

	@PostMapping("/logout")
	public ResponseEntity<Message> kakaoLogout(HttpSession session) {
		String user = (String) session.getAttribute("user");
		String nickname = (String) session.getAttribute("nickname");

		if (user != null) {
			session.invalidate();

			return ResponseEntity
				.status(HttpStatus.OK)
				.body(new Message(HttpStatus.OK, "로그아웃 성공", nickname));
		}

		return ResponseEntity
			.status(HttpStatus.NOT_FOUND)
			.body(new Message(HttpStatus.NOT_FOUND, "로그아웃 실패", null));
	}
}
