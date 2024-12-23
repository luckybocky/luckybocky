package com.project.luckybocky.user.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@AllArgsConstructor
@ToString
public class UserDto {
	private String userKey;
	private String userNickname;
	private String firebaseKey;
	private Boolean alarmStatus;
	private Boolean fortuneVisibility;
}
