package com.project.luckybocky.push.enums;

import lombok.Getter;

@Getter
public enum PushMessage {
	ARTICLE("복이 들어왔어요!", " 님이 복을 넣었어요!"),
	COMMENT("답장이 남겨졌어요!", " 님이 답장을 남겼어요");

	private final String title;
	private final String body;

	PushMessage(String title, String body) {
		this.title = title;
		this.body = body;
	}

	// String을 NotificationType으로 변환하는 메서드
	public static PushMessage fromString(String type) {
		try {
			return PushMessage.valueOf(type.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("유효하지 않은 알림 타입입니다: " + type);
		}
	}
}
