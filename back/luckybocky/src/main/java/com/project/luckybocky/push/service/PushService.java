package com.project.luckybocky.push.service;

import com.project.luckybocky.push.dto.PushDto;

public interface PushService {
	void sendPush(PushDto pushDto);
}
