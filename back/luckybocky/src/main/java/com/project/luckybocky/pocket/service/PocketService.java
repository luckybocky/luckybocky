package com.project.luckybocky.pocket.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.luckybocky.pocket.dto.PocketDto;
import com.project.luckybocky.pocket.dto.PocketInfoDto;
import com.project.luckybocky.pocket.entity.Pocket;
import com.project.luckybocky.pocket.exception.PocketNotFoundException;
import com.project.luckybocky.pocket.repository.PocketRepository;
import com.project.luckybocky.user.entity.User;
import com.project.luckybocky.user.exception.UserNotFoundException;
import com.project.luckybocky.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class PocketService {
	private final UserRepository userRepository;
	private final PocketRepository pocketRepository;

	public String getPocketAddress(String userKey) {
		User user = userRepository.findByUserKey(userKey)
			.orElseThrow(() -> new UserNotFoundException());
		Pocket pocket = pocketRepository.findPocketByUser(user)
			.orElseThrow(() -> new PocketNotFoundException());

		return pocket.getPocketAddress();
	}

	public PocketDto getPocketByAddress(String url) {
		Pocket pocket = pocketRepository.findPocketByPocketAddress(url)
			.orElseThrow(() -> new PocketNotFoundException());
		return pocket.pocket();
	}

	public String createPocket(String userKey) {
		User user = userRepository.findByUserKey(userKey)
			.orElseThrow(() -> new UserNotFoundException());

		//uuid로 복주머니 링크 생성 후 저장 & 반환
		UUID uuid = UUID.randomUUID();
		String address = uuid.toString();

		Pocket pocket = Pocket.builder().user(user).pocketAddress(address).build();
		pocketRepository.save(pocket);

		log.info("유저 {}, 복주머니 {}", user.getUserSeq(), pocket.getPocketSeq());

		return address;
	}

	public PocketInfoDto getPocketInfoByUser(String userKey) {
		User user = userRepository.findByUserKey(userKey)
			.orElseThrow(() -> new UserNotFoundException());
		Pocket pocket = pocketRepository.findFirstByUserOrderByCreatedAtDesc(user)
			.orElseThrow(() -> new PocketNotFoundException());
		return pocket.pocketInfo();
	}
}
