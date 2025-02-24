package com.project.luckybocky.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class QnaAdminReqDto {
	@Schema(description = "등록한 답변")
	private String answer;
}
