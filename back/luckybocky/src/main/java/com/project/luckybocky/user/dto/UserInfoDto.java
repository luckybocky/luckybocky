package com.project.luckybocky.user.dto;

import lombok.Builder;

@Builder
public class UserInfoDto {
    private String userNickname;
    private boolean alarmStatus;
    private boolean fortuneVisibility;
    private String createdAt;
}
