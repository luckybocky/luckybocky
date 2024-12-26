package com.project.luckybocky.push.controller;


import com.project.luckybocky.common.MessageDto;
import com.project.luckybocky.push.dto.PushDto;
import com.project.luckybocky.push.service.PushService;
import com.project.luckybocky.user.dto.UserDto;
import com.project.luckybocky.user.entity.User;
import com.project.luckybocky.user.service.UserSettingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/push")
//이 클래스가 REST API 컨트롤러임을 나타냅니다.
//반환값은 JSON 형식으로 자동 변환됩니다.
//없으면 반환값을 이름으로하는 뷰를 찾으려고 시도
@RestController
@Slf4j
@RequiredArgsConstructor

public class PushController {

    private final PushService pushService;
    private final UserSettingService userSettingService;

    @PostMapping("/comment")
    public ResponseEntity<MessageDto> pushComment(@RequestBody PushDto pushDto) {
        boolean isSuccess = pushService.sendPush(pushDto.getUserKey(), "temp", pushDto.getTitle(), pushDto.getBody());
        if (isSuccess) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    MessageDto.builder()
                            .status("success")
                            .message("push successful")
                            .build()
            );
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    MessageDto.builder()
                            .status("error")
                            .message("Unauthorized")
                            .build()

            );
        }
    }

}


