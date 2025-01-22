package com.project.luckybocky.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class UserInfoDto {
    private String userNickname;
    private boolean alarmStatus;
    private boolean fortuneVisibility;
    private String createdAt;
}
