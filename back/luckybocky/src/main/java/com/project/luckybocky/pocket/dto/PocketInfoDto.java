package com.project.luckybocky.pocket.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class PocketInfoDto {
	@Schema(description = "복주머니 번호")
	private int pocketSeq;
	@Schema(description = "복주머니 주소")
	private String pocketAddress;
	@Schema(description = "복주머니 주인 번호")
	private int userSeq;
	@Schema(description = "복주머니 주인 식별키")
	private String userKey;
	@Schema(description = "복주머니 주인 닉네임")
	private String userNickname;
	@Schema(description = "복주머니 공개 여부")
	private boolean fortuneVisibility;
}
