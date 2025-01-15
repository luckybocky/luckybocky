package com.project.luckybocky.push.service;

import org.springframework.stereotype.Service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.project.luckybocky.article.service.ArticlePushService;
import com.project.luckybocky.pocket.service.PocketPushService;
import com.project.luckybocky.push.dto.PushDto;
import com.project.luckybocky.push.enums.PushMessage;
import com.project.luckybocky.user.entity.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class PushServiceImpl implements PushService {
	private final ArticlePushService articlePushService;
	private final PocketPushService pocketPushService;
	private final String accessUrl = "luckybocky";

	//복주머니에 복이 달렸을때, 복주머니 주인에게 푸시 알림을 보내야한다
	public void sendPocketPush(PushDto pushDto) throws FirebaseMessagingException {
		int pocketSeq = pushDto.getContentSeq();
		String url = pushDto.getUrl();
		String fromUser = pushDto.getFromUser();

		//복주머니에 복이 들어왔기 때문에, 복 관련 메시지
		PushMessage pushMessage = PushMessage.ARTICLE;

		User toUser = pocketPushService.findPocketOwner(pocketSeq);
		String title = pushMessage.getTitle();
		String body = fromUser + pushMessage.getBody();

		if (!canPush(toUser, pushDto)) {
			return;
		}

		Notification notification = getNotification(body, title);

		Message message = getMessage(notification, toUser.getFirebaseKey(), url);

		String response = FirebaseMessaging.getInstance().send(message);
	}

	public void sendArticlePush(PushDto pushDto) throws FirebaseMessagingException {
		int articleSeq = pushDto.getContentSeq();
		String url = pushDto.getUrl();
		String fromUser = pushDto.getFromUser();

		//복에 리복이 들어왔기 때문에, 리복 관련 메시지
		PushMessage pushMessage = PushMessage.COMMENT;

		//복에 리복이 달렸을때, 복 주인에게 푸시를 보내야한다.
		User toUser = articlePushService.findArticleOwner(articleSeq);
		String title = pushMessage.getTitle();
		String body = fromUser + pushMessage.getBody();

		if (!canPush(toUser, pushDto)) {
			return;
		}

		Notification notification = getNotification(body, title);

		Message message = getMessage(notification, toUser.getFirebaseKey(), url);

		String response = FirebaseMessaging.getInstance().send(message);
	}

	private Message getMessage(Notification notification, String firebaseKey, String url) {
		return Message.builder()
			.setNotification(notification)
			.putData("url", url)
			.setToken(firebaseKey)
			.build();
	}

	private Notification getNotification(String title, String body) {
		return Notification.builder()
			.setTitle(title)
			.setBody(body)
			.build();
	}

	public boolean canPush(User user, PushDto pushDto) {
		if (user.getUserKey() == null) {
			log.info("{} 님은 비회원이기 때문에 푸시전달을 할 수 없습니다.", pushDto.getFromUser());
			return false;
		}

		if (user.getFirebaseKey() == null
			|| user.getFirebaseKey().isEmpty()) {
			log.info("{}({}) 님의 파이어베이스키를 확인 할 수 없습니다.", user.getUserKey(), user.getUserNickname());
			return false;
		}

		if (!user.isAlarmStatus()) {
			log.info("{}({}) 님의 푸시 설정이 꺼져있습니다.", user.getUserKey(), user.getUserNickname());
			return false;
		}


		// if(!pushDto.getUrl().contains(accessUrl)){
		// 	log.info("{} 님이 보낸 url은 변조되었습니다({}).", user.getUserKey(), pushDto.getUrl());
		// 	return false;
		// }


		return true;
	}

	// public User findUser(String type, int contentSeq) {
	// 	if (type.equals(PushMessage.ARTICLE.name())) {
	// 		//푸시 타입이 ARTICLE이면 복주머니 주인한테 푸시 보내야한다
	// 		return pocketPushService.findPocketOwner(contentSeq);
	// 	} else if (type.equals(PushMessage.COMMENT.name())) {
	// 		//푸시 타입이 Comment이면 복 주인한테 푸시 보내야한다
	// 		return articlePushService.findArticleOwner(contentSeq);
	// 	}
	//
	// 	throw new IllegalArgumentException("푸시 타입이 아닙니다.");
	// }

}
