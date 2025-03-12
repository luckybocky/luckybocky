package com.project.luckybocky.user.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.luckybocky.user.dto.UserLoginDto;
import com.project.luckybocky.user.entity.User;
import com.project.luckybocky.user.exception.UserNotFoundException;
import com.project.luckybocky.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class UserSettingServiceImpl implements UserSettingService {

	private final UserRepository userRepository;

	@Override
	public void updateUserSetting(String userKey, String userNickname, boolean alarmStatus, boolean fortuneVisibility) {

		Optional<User> userOptional = userRepository.findByUserKey(userKey);

		if (userOptional.isEmpty()) {
			throw new UserNotFoundException();
		}

		User user = userOptional.get();
		user.updateUserInfo(userNickname, alarmStatus, fortuneVisibility);
	}

	@Override
	public UserLoginDto getUserLogin(String userKey) {
		Optional<User> userOptional = userRepository.findByUserKey(userKey);

		//로그인이 되어있지 않는경우
		if (userOptional.isEmpty()) {
			log.info("비회원 입니다. 정보를 조회할 수 없습니다.");
			return User.getNonMemberInfo();
		}

		User user = userOptional.get();

		log.info("회원정보를 조회합니다. {}", user.getMemberInfo());

		return user.getMemberInfo();
	}

	@Override
	public void updateFireBaseKey(String userKey, String firebaseKey) {
		Optional<User> userOptional = userRepository.findByUserKey(userKey);

		if (userOptional.isEmpty()) {
			log.info("사용자를 찾을 수 없어, 파이어베이스 키를 업데이트 할 수 없습니다.");
			throw new UserNotFoundException();
		}

		log.info("파이어베이스키를 업데이트 합니다. {} : 키 길이({})", userKey, firebaseKey.length());
		User user = userOptional.get();
		user.setFirebaseKey(firebaseKey);
	}


	@Override
	public Optional<User> findUserFirebaseKey(String userKey) {
		return userRepository.findByUserKey(userKey);
	}
}
