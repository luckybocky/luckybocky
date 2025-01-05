package com.project.luckybocky.user.controller;


import com.project.luckybocky.common.DataResponseDto;
import com.project.luckybocky.common.ResponseDto;
import com.project.luckybocky.user.dto.SettingDto;
import com.project.luckybocky.user.dto.UserInfoDto;
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
@RequestMapping("api/v1/auth/user" )
public class SettingController {
    private final UserSettingService userSettingService;


    @PutMapping
    public ResponseEntity<ResponseDto> updateSetting(@RequestBody SettingDto settingDto, HttpSession session) {
        String userKey = (String) session.getAttribute("user" );
        userSettingService.updateUserSetting(userKey, settingDto.getUserNickname(), settingDto.getAlarmStatus(), settingDto.getFortuneVisibility());
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("setting successful" ));
    }

    @GetMapping
    public ResponseEntity<DataResponseDto<UserInfoDto>> loadUserInfo(HttpSession session) {
        String userKey = (String) session.getAttribute("user" );
        UserInfoDto userInfoDto = userSettingService.getUserInfo(userKey);
        log.info("user found {} {}", userKey, userInfoDto);
        return ResponseEntity.status(HttpStatus.OK).body(new DataResponseDto<>("not found user", userInfoDto));
    }
}
