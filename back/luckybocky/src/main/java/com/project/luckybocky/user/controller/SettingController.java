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

//    @PostMapping
//    public ResponseEntity<User> userSave(@RequestBody User user){
//        User saveUser = userSettingService.join(user);
//        log.info("{}", user.toString());
//        return ResponseEntity.status(HttpStatus.OK).body(saveUser);
//    }

    @PutMapping
    public ResponseEntity<ResponseDto> updateSetting(@RequestBody SettingDto settingDto, HttpSession session) {
        String userKey = (String) session.getAttribute("user" );
        boolean isSuccess = userSettingService.updateUserSetting(userKey, settingDto.getUserNickname(), settingDto.getAlarmStatus(), settingDto.getFortuneVisibility());
        if (!isSuccess) {
            log.info("setting user {}", "UNAUTHORIZED" );
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseDto("Unauthorized" ));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("setting successful" ));
        }
    }

    @GetMapping
    public ResponseEntity<DataResponseDto<UserInfoDto>> loadUserInfo(HttpSession session) {
        String userKey = (String) session.getAttribute("user" );
        UserInfoDto userInfoDto = userSettingService.getUserInfo(userKey);
        log.info("user found {} {}", userKey, userInfoDto);
        if (userInfoDto == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new DataResponseDto<UserInfoDto>("not found user", null));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new DataResponseDto<UserInfoDto>("not found user", userInfoDto));
    }
}
