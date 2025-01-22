package com.project.luckybocky.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
@AllArgsConstructor
public class UserLoginDto {
	private UserInfoDto userInfo;

	private boolean isLogin;



}
