package com.project.luckybocky.user.controller;


import com.project.luckybocky.common.MessageDto;
import com.project.luckybocky.user.dto.SettingDto;
import com.project.luckybocky.user.entity.User;
import com.project.luckybocky.user.repository.UserSettingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/user")
public class SettingController {
    private final UserSettingRepository userSettingRepository;

    @PostMapping
    public ResponseEntity<User> userSave(@RequestBody User user){
        User saveUser = userSettingRepository.save(user);
        log.info("{}", user.toString());
        return ResponseEntity.status(HttpStatus.OK).body(saveUser);
    }

    @PutMapping
    public ResponseEntity<MessageDto> updateSetting(@RequestBody SettingDto settingDto){
        Optional<User> userOptional = userSettingRepository.findByKey(settingDto.getUserKey());

        if(userOptional.isEmpty()){
            log.info("setting user {}", "UNAUTHORIZED");
            MessageDto messageDto = MessageDto.builder()
                    .status("error")
                    .message("Unauthorized")
                    .build();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(messageDto);
        }

        User user = userOptional.get();
        log.info("setting user {}", user);
        user.setAlarmStatus(settingDto.getAlarmStatus());
        user.setFortuneVisibility(settingDto.getFortuneVisibility());

        userSettingRepository.save(user);

        MessageDto messageDto = MessageDto.builder()
                .status("success")
                .message("setting successful")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(messageDto);

    }



}
