package com.project.luckybocky.push.service;


import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PushServiceImpl implements PushService {
//    private final UserSettingRepository userSettingRepository;
//
//
//    public boolean sendPush(String toUserKey, String fromUserKey, String title, String body) {
//        Optional<User> userOptional = userSettingRepository.findByKey(toUserKey);
//
//        if(userOptional.isEmpty()){
//            return false;
//        }
//
//        User user = userOptional.get();
//
//        //알림을 허용한 사람한테만 보낸다.
//        if(!user.isAlarm()){
//            log.info("push off {}", user.getUserNickname());
//            return false;
//        }
//
//        Notification notification= Notification.builder()
//                .setBody(body)
//                .setTitle(fromUserKey+"님이 보냄 " + title)
//                .build();
//
//        Message message = Message.builder().setNotification(notification).setToken(user.getFirebaseKey()).build();
//
//        try {
//            String response = FirebaseMessaging.getInstance().send(message);
//            log.info("push message {}", "comment 알림 전송에 성공하였습니다.");
//        } catch (Exception e) {
//            log.warn("push message {}", ": comment 알림 전송에 실패하였습니다.");
//            return false;
//        }
//
//        return true;
//    }

    public boolean sendPush(String toUserKey, String fromUserKey, String title, String body) {
        return false;
    }

}
