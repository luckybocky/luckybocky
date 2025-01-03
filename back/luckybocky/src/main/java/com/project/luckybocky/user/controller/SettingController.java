package com.project.luckybocky.user.controller;


import com.project.luckybocky.common.MessageDto;
import com.project.luckybocky.user.dto.SettingDto;
import com.project.luckybocky.user.dto.UserInfoDto;
import com.project.luckybocky.user.entity.User;
import com.project.luckybocky.user.service.UserSettingService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth/user")
public class SettingController {
    private final UserSettingService userSettingService;

    @PostMapping
    public ResponseEntity<User> userSave(@RequestBody User user){
        User saveUser = userSettingService.join(user);
        log.info("{}", user.toString());
        return ResponseEntity.status(HttpStatus.OK).body(saveUser);
    }

    @PutMapping
    public ResponseEntity<MessageDto> updateSetting(@RequestBody SettingDto settingDto, HttpSession session) {
        String userKey = (String) session.getAttribute("user");
        boolean isSuccess = userSettingService.updateUserSetting(userKey,settingDto.getUserNickname(), settingDto.getAlarmStatus(), settingDto.getFortuneVisibility());
        if (!isSuccess) {
            log.info("setting user {}", "UNAUTHORIZED");
            MessageDto messageDto = MessageDto.builder()
                    .status("error")
                    .message("Unauthorized")
                    .build();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(messageDto);
        } else {
            MessageDto messageDto = MessageDto.builder()
                    .status("success")
                    .message("setting successful")
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(messageDto);
        }
    }

    @GetMapping
    public ResponseEntity<UserInfoDto> loadUserInfo(HttpSession session){
        String userKey = (String) session.getAttribute("user");
        UserInfoDto userInfoDto = userSettingService.getUserInfo(userKey);
        log.info("user found {} {}",userKey, userInfoDto);
        if(userInfoDto == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(userInfoDto);
        }
        return ResponseEntity.status(HttpStatus.OK).body(userInfoDto);
    }
}
