package com.project.luckybocky.admin.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAdminDto {
	private Integer userSeq;
	private String userKey;
	private String userNickname;
	private String firebaseKey;
	private boolean alarmStatus;
	private boolean fortuneVisibility;
	private byte role;
	private String pocketAddress;
	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;
	private Boolean isDeleted;
}
