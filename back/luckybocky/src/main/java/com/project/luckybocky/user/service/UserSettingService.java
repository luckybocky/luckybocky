package com.project.luckybocky.user.service;

import com.project.luckybocky.user.dto.UserInfoDto;

public interface UserSettingService {
    boolean updateUserSetting(String userKey,boolean alarmStatus,boolean fortuneVisibility);
    UserInfoDto findByUserKey(String userKey);
    boolean updateFireBaseKey(String userKey, String firebaseKey);

}