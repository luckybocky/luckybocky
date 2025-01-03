package com.project.luckybocky.push.controller;


import com.google.api.Http;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.project.luckybocky.common.MessageDto;
import com.project.luckybocky.push.dto.PushDto;
import com.project.luckybocky.push.enums.PushMessage;
import com.project.luckybocky.push.service.PushService;
import com.project.luckybocky.user.dto.UserDto;
import com.project.luckybocky.user.entity.User;
import com.project.luckybocky.user.service.UserSettingService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/v1/push")
//이 클래스가 REST API 컨트롤러임을 나타냅니다.
//반환값은 JSON 형식으로 자동 변환됩니다.
//없으면 반환값을 이름으로하는 뷰를 찾으려고 시도
@RestController
@Slf4j
@RequiredArgsConstructor

public class PushController {

    private final PushService pushService;


    @PostMapping
    public ResponseEntity<MessageDto> pushContent(HttpSession session, @RequestBody PushDto pushDto) {

        String fromUser = (String) session.getAttribute("user");
        log.info(" Push Info : {}",pushDto);
        try {
            pushService.sendPush(fromUser, pushDto);
            return ResponseEntity.status(HttpStatus.OK).body(
                    MessageDto.builder()
                            .status("success")
                            .message("push success")
                            .build()

            );
        } catch (IllegalArgumentException | NullPointerException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    MessageDto.builder()
                            .status("fail")
                            .message(e.getMessage())
                            .build()

            );
        } catch (FirebaseMessagingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body(
                            MessageDto.builder()
                                    .status("fail")
                                    .message("푸시알림 전송에 실패했습니다.")
                                    .build()
                    );
        }
    }


//    @PostMapping("/test")
//    public ResponseEntity<MessageDto> pushComment(HttpSession session, @RequestBody PushDto pushDto) {
//
//        session.setAttribute("user","changhee");
//
//        String toUser = pushDto.getToUser();
//        String fromUser = (String) session.getAttribute("user");
//        String type = pushDto.getType();
//        String url = pushDto.getUrl();
//
//        log.info("toUser : {}, fromUser : {}", toUser,session.getAttribute("user"));
//        try {
//            pushService.sendPush(toUser, fromUser, type,url);
//            return ResponseEntity.status(HttpStatus.OK).body(
//                    MessageDto.builder()
//                            .status("success")
//                            .message("push success")
//                            .build()
//
//            );
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
//                    MessageDto.builder()
//                            .status("fail")
//                            .message("없는 사용자거나 푸시 타입이 잘못되었습니다.")
//                            .build()
//
//            );
//        } catch (FirebaseMessagingException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
//                    body(
//                            MessageDto.builder()
//                                    .status("fail")
//                                    .message("푸시알림 전송에 실패했습니다.")
//                                    .build()
//                    );
//        }
//    }

}


