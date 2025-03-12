package com.project.luckybocky.push.dto;

import com.project.luckybocky.user.entity.User;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PushDto {
	@Schema(description = "푸시알림을 보내는 유저정보")
	private User toUser;

	@Schema(description = "푸시알림을 클릭하면 이동할 주소")
	private String address;

	@Schema(description = "푸시알림 제목")
	private String title;

	@Schema(description = "푸시알림 내용")
	private String content;
}
