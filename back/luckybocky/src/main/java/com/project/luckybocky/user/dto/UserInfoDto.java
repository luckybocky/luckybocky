package com.project.luckybocky.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class UserInfoDto {
    @Schema(description = "유저의 닉네임")
    private String userNickname;

    @Schema(description = "유저의 푸시알림 허용 여부")
    private boolean alarmStatus;

    @Schema(description = "복주머니 공개여부")
    private boolean fortuneVisibility;

    @Schema(description = "유저가 가입한 날짜")
    private String createdAt;
}
