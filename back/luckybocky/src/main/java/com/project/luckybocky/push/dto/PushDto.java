package com.project.luckybocky.push.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PushDto {
	private String type;
	private String userKey;
}
