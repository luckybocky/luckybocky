package com.project.luckybocky.user.service;

import com.project.luckybocky.user.dto.UserInfoDto;
import com.project.luckybocky.user.dto.UserLoginDto;
import com.project.luckybocky.user.entity.User;

import java.util.Optional;

public interface UserSettingService {

    void updateUserSetting(String userKey,String userNickname, boolean alarmStatus,boolean fortuneVisibility);
    UserLoginDto getUserLogin(String userKey);
    Optional<User> findUserFirebaseKey(String userKey);

    void updateFireBaseKey(String userKey, String firebaseKey);
}