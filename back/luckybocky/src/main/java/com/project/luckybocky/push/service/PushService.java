package com.project.luckybocky.push.service;

public interface PushService {
	boolean sendPush(String toUserKey, String fromUserKey, String title,String body);
}
