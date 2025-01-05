package com.project.luckybocky.push.service;


import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.project.luckybocky.article.service.ArticlePushService;
import com.project.luckybocky.pocket.service.PocketPushService;
import com.project.luckybocky.push.dto.PushDto;
import com.project.luckybocky.push.enums.PushMessage;
import com.project.luckybocky.user.entity.User;
import com.project.luckybocky.user.exception.UserNotFoundException;
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
    private final ArticlePushService articlePushService;
    private final PocketPushService pocketPushService;


    public void sendPush(String fromUser, PushDto pushDto) throws FirebaseMessagingException{
        String type = pushDto.getType().toUpperCase();
        String url =pushDto.getUrl();
        int contentSeq = pushDto.getContentSeq();

        String toUser = findUser(type,contentSeq);


        Optional<User> userOptional = userSettingService.findUserFirebaseKey(toUser);



        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("not found user");
        }

        PushMessage pushMessage = PushMessage.fromString(type);
        User user = userOptional.get();
        String title = pushMessage.getTitle();
        String body = fromUser+pushMessage.getBody();

        //알림을 허용한 사람한테만 보낸다.
        if (!user.isAlarmStatus()) {
            log.info("{} is push off", user.getUserNickname());
            return;
        }

        Notification notification = Notification.builder()
                .setBody(body)
                .setTitle(title)
                .build();

        Message message = Message.builder().setNotification(notification).putData("url", url).setToken(user.getFirebaseKey()).build();

        String response = FirebaseMessaging.getInstance().send(message);
    }

    public String findUser(String type, int contentSeq){
        if(type.equals(PushMessage.ARTICLE.name())){
            //푸시 타입이 ARTICLE이면 복주머니 주인한테 푸시 보내야한다
            return pocketPushService.findPocket(contentSeq);
        }else if(type.equals(PushMessage.COMMENT.name())){
            //푸시 타입이 Comment이면 복 주인한테 푸시 보내야한다
            return articlePushService.findPocketOwner(contentSeq);
        }

        throw new IllegalArgumentException("푸시 타입이 아닙니다.");
    }



}
