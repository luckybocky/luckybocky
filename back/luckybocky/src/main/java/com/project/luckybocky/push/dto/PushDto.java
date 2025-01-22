package com.project.luckybocky.push.dto;

import com.project.luckybocky.user.entity.User;

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
	// private int contentSeq;
	private User toUser;
	private String address;
	private String title;
	private String content;
}
