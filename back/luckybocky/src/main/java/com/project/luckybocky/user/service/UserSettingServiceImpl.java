package com.project.luckybocky.user.service;

import com.project.luckybocky.user.dto.UserInfoDto;
import com.project.luckybocky.user.entity.User;
import com.project.luckybocky.user.exception.UserNotFoundException;
import com.project.luckybocky.user.repository.UserSettingRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class UserSettingServiceImpl implements UserSettingService {

	private final UserSettingRepository userSettingRepository;

	@Override
	public void updateUserSetting(String userKey, String userNickname, boolean alarmStatus, boolean fortuneVisibility) {

		Optional<User> userOptional = userSettingRepository.findByKey(userKey);

		if (userOptional.isEmpty()) {
			throw new UserNotFoundException();
		}

		User user = userOptional.get();
		log.info("updateUserSetting : {}", user);
		user.updateUserInfo(userNickname, alarmStatus, fortuneVisibility);
	}

	@Override
	public UserInfoDto getUserInfo(String userKey) {
		Optional<User> userOptional = userSettingRepository.findByKey(userKey);

		if (userOptional.isEmpty()) {
			throw new UserNotFoundException();
		}

		User user = userOptional.get();
		log.info("findByUserKey {}", user);

		return user.getUserInfo();
	}

	@Override
	public void updateFireBaseKey(String userKey, String firebaseKey) {
		Optional<User> userOptional = userSettingRepository.findByKey(userKey);

		if (userOptional.isEmpty()) {
			throw new UserNotFoundException();
		}

		User user = userOptional.get();
		user.setFirebaseKey(firebaseKey);
	}

	@Override
	public User join(User user) {
		return userSettingRepository.save(user);
	}

	@Override
	public Optional<User> findUserFirebaseKey(String userKey) {
		return userSettingRepository.findByKey(userKey);
	}
}
