package com.project.luckybocky.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
	@Schema(description = "유저의 식별자 키")
	private String userKey;

	@Schema(description = "유저의 닉네임")
	private String userNickname;

	@Schema(description = "알림전송을 위한 파이어베이스키")
	private String firebaseKey;

	@Schema(description = "유저의 푸시알림 허용 여부")
	private Boolean alarmStatus;

	@Schema(description = "복주머니 공개여부")
	private Boolean fortuneVisibility;
}
