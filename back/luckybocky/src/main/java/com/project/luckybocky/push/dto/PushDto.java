package com.project.luckybocky.push.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PushDto {
	private int contentSeq;
	private String fromUser;
	private String url;
}
