package com.project.luckybocky.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class SettingDto {
    @Schema(description = "유저의 닉네임")
    private String userNickname;

    @Schema(description = "유저의 푸시알림 허용 여부")
    private Boolean alarmStatus;

    @Schema(description = "복주머니 공개여부")
    private Boolean fortuneVisibility;
}
