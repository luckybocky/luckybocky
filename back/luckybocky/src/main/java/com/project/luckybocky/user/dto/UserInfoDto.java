package com.project.luckybocky.user.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserInfoDto {
    private String userNickname;
    private boolean alarmStatus;
    private boolean fortuneVisibility;
    private String createdAt;
}
