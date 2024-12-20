package com.project.luckybocky.push.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PushDto {
	private String userKey;
	private String title;
	private String body;
}
