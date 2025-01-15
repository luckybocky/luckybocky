package com.project.luckybocky.push.service;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.project.luckybocky.push.dto.PushDto;

public interface PushService {
	void sendPocketPush(PushDto pushDto) throws FirebaseMessagingException;
	void sendArticlePush(PushDto pushDto) throws FirebaseMessagingException;
}
