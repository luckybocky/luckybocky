package com.project.luckybocky.kakao.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.luckybocky.kakao.dto.KakaoLoginDto;
import com.project.luckybocky.user.repository.UserRepository;
import com.project.luckybocky.user.dto.UserDto;
import com.project.luckybocky.user.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KakaoService {
	private final RestTemplate restTemplate;
	private final UserRepository userRepository;

	@Value("${oauth.kakao.client_id}")
	private String clientId;

	@Value("${oauth.kakao.redirect_uri}")
	private String redirectUri;

	public String getAuthorizationCode() {
		return String.format(
			"https://kauth.kakao.com/oauth/authorize?"
				+ "response_type=code&"
				+ "client_id=%s"
				+ "&redirect_uri=%s",
			clientId, redirectUri);
	}

	public String getToken(String code) {
		String tokenUri = "https://kauth.kakao.com/oauth/token";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("grant_type", "authorization_code");
		body.add("client_id", clientId);
		body.add("redirect_uri", redirectUri);
		body.add("code", code);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
		ResponseEntity<KakaoLoginDto> response = restTemplate.postForEntity(
			tokenUri,
			request,
			KakaoLoginDto.class);

		if(response.getBody() != null) {
			return response.getBody().getAccess_token();
		}

		return null;
	}

	public UserDto getLoginUser(String accessToken) throws JsonProcessingException {
		String userInfoUri = "https://kapi.kakao.com/v2/user/me";

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(accessToken);
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);
		ResponseEntity<String> response = restTemplate.exchange(
			userInfoUri,
			HttpMethod.GET,
			request,
			String.class);

		if(response.getBody() != null) {
			ObjectMapper objectMapper = new ObjectMapper();
			String responseBody = response.getBody();
			JsonNode jsonNode = objectMapper.readTree(responseBody);
			return UserDto.builder()
				.userKey("K" + jsonNode.get("id").asText())
				.alarmStatus(false)
				.fortuneVisibility(false)
				.build();
		}

		return null;
	}

	public UserDto kakaoLogin(String code) throws JsonProcessingException {
		String accessToken = getToken(code);

		if(accessToken != null) {
			UserDto userDto = getLoginUser(accessToken);
			userRepository.findByUserKey(userDto.getUserKey())
				.orElseGet(() -> userRepository.save(
					User.builder()
						.userKey(userDto.getUserKey())
						.userNickname(userDto.getUserNickname())
						.firebaseKey(userDto.getFirebaseKey())
						.alarmStatus(userDto.getAlarmStatus())
						.fortuneVisibility(userDto.getFortuneVisibility())
						.build()
				));
			return userDto;
		}
		return null;
	}
}
