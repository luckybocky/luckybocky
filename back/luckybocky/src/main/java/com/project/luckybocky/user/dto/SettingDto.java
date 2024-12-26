package com.project.luckybocky.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class SettingDto {
    private Boolean alarmStatus;
    private Boolean fortuneVisibility;
}