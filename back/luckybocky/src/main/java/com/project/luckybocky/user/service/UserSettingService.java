package com.project.luckybocky.user.service;

import com.project.luckybocky.article.dto.MyArticlesDto;
import com.project.luckybocky.user.dto.UserDto;
import com.project.luckybocky.user.dto.UserInfoDto;
import com.project.luckybocky.user.entity.User;

import java.util.Optional;

public interface UserSettingService {
    boolean updateUserSetting(String userKey,String userNickname, boolean alarmStatus,boolean fortuneVisibility);
    UserInfoDto getUserInfo(String userKey);
    Optional<User> findUserFirebaseKey(String userKey);
    boolean updateFireBaseKey(String userKey, String firebaseKey);
    User join(User user);
}