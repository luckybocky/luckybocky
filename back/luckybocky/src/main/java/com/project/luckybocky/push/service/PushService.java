package com.project.luckybocky.push.service;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.project.luckybocky.push.dto.PushDto;

public interface PushService {
	void sendPush(String fromUser, PushDto pushDto) throws FirebaseMessagingException;
}
