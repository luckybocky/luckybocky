package com.project.luckybocky.push.service;

import com.google.firebase.messaging.FirebaseMessagingException;

public interface PushService {
	void sendPush(String toUserKey, String fromUserKey, String type) throws FirebaseMessagingException;
}
