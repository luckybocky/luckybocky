package com.project.luckybocky.push.service;


import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.project.luckybocky.push.enums.PushMessage;
import com.project.luckybocky.user.entity.User;
import com.project.luckybocky.user.repository.UserSettingRepository;
import com.project.luckybocky.user.service.UserSettingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PushServiceImpl implements PushService {
    private final UserSettingService userSettingService;


    public void sendPush(String toUserKey, String fromUserKey, String type) throws FirebaseMessagingException {
        Optional<User> userOptional = userSettingService.findUserFirebaseKey(toUserKey);

        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("사용자를 찾을 수 없습니다.");
        }

        PushMessage pushMessage = PushMessage.fromString(type);
        User user = userOptional.get();
        String title = pushMessage.getTitle();
        String body = pushMessage.getBody();

        //알림을 허용한 사람한테만 보낸다.
        if (!user.isAlarmStatus()) {
            log.info("{} is push off", user.getUserNickname());
            return;
        }

        Notification notification = Notification.builder()
                .setBody(body)
                .setTitle(title)
                .build();

        Message message = Message.builder().setNotification(notification).setToken(user.getFirebaseKey()).build();

        String response = FirebaseMessaging.getInstance().send(message);
    }


}
