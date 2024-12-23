package com.project.luckybocky.login.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class LoginDto {
	private String tokenType;  //  토큰 타입, bearer로 고정
	private String accessToken; //	사용자 액세스 토큰 값
	private Integer expiresIn;  //  액세스 토큰과 ID 토큰의 만료 시간(초)
	private String refreshToken; //  사용자 리프레시 토큰 값
	private Integer refreshTokenExpiresIn;  // 리프레시 토큰 만료 시간(초)
	private String scope;  // 인증된 사용자의 정보 조회 권한 범위 -> 개인정보 동의 항목
}
