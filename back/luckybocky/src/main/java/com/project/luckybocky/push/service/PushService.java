package com.project.luckybocky.push.service;

import com.google.firebase.messaging.FirebaseMessagingException;

public interface PushService {
	void sendPush(String toUser, String fromUser, String type,String url) throws FirebaseMessagingException;
}
