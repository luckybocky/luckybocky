package com.project.luckybocky.user.service;

import com.project.luckybocky.article.entity.Article;
import com.project.luckybocky.user.dto.MyArticleDto;
import com.project.luckybocky.user.dto.MyArticlesDto;
import com.project.luckybocky.user.dto.UserInfoDto;
import com.project.luckybocky.user.entity.User;
import com.project.luckybocky.user.repository.UserSettingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class UserSettingServiceImpl implements UserSettingService{

    private final UserSettingRepository userSettingRepository;

    @Override
    public boolean updateUserSetting(String userKey, boolean alarmStatus,boolean fortuneVisibility){
        Optional<User> userOptional = userSettingRepository.findByKey(userKey);

        if(userOptional.isEmpty()){
            return false;
        }

        User user = userOptional.get();
        log.info("setting user {}", user);
        user.setAlarmStatus(alarmStatus);
        user.setFortuneVisibility(fortuneVisibility);

        return true;
    }
    @Override
    public UserInfoDto findByUserKey(String userKey) {
        Optional<User> userOptional = userSettingRepository.findByKey(userKey);

        if(userOptional.isEmpty()){
            return null;
        }

        User user = userOptional.get();
        log.info("findByUserKey {}", user);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = user.getCreatedAt().format(formatter);

        return UserInfoDto.builder()
                .userNickname(user.getUserNickname())
                .alarmStatus(user.isAlarmStatus())
                .fortuneVisibility(user.isFortuneVisibility())
                .createdAt(formattedDate)
                .build();
    }

    @Override
    public boolean updateFireBaseKey(String userKey, String firebaseKey) {
        Optional<User> userOptional = userSettingRepository.findByKey(userKey);

        if(userOptional.isEmpty()){
            return false;
        }

        User user = userOptional.get();
        user.setFirebaseKey(firebaseKey);
        return true;
    }

    @Override
    public MyArticlesDto findMyArticles(String userKey) {
        List<Article> articleByUserKey = userSettingRepository.findArticleByUserKey(userKey);
        List<MyArticleDto> myArticles = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for(Article article : articleByUserKey){
            String articleOwner = article.getUserNickname();
            String content = article.getArticleContent();
            String fortuneName = article.getFortune().getFortuneName();
            String fortuneImg = article.getFortune().getFortuneImg();
            String createdAt =  article.getCreatedAt().format(formatter);

            myArticles.add(new MyArticleDto(articleOwner,content,fortuneName,fortuneImg,createdAt));
        }

        return new MyArticlesDto(myArticles);
    }
}
