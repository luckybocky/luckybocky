package com.project.luckybocky.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.luckybocky.user.exception.UserNotFoundException;
import com.project.luckybocky.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserService {
	private final UserRepository userRepository;

	public int getUserSeq(String userKey) {
		return userRepository.findByUserKey(userKey)
			.orElseThrow(() -> new UserNotFoundException())
			.getUserSeq();
	}
}
