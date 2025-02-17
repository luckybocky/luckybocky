package com.project.luckybocky.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
@AllArgsConstructor
public class UserLoginDto {
	@Schema(description = "로그인한 유저의 정보")
	private UserInfoDto userInfo;

	@Schema(description = "유저의 로그인 여부")
	private boolean isLogin;



}
