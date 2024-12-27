package com.project.luckybocky.user.service;

import com.project.luckybocky.article.dto.MyArticlesDto;
import com.project.luckybocky.user.dto.SettingDto;
import com.project.luckybocky.user.dto.UserDto;
import com.project.luckybocky.user.dto.UserInfoDto;
import com.project.luckybocky.user.entity.User;

public interface UserSettingService {
    boolean updateUserSetting(String userKey, SettingDto settingDto);
    UserInfoDto findByUserKey(String userKey);
    boolean updateFireBaseKey(String userKey, String firebaseKey);
    User join(User user);


}