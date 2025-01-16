package com.project.luckybocky.pocket.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.luckybocky.pocket.entity.Pocket;
import com.project.luckybocky.pocket.exception.PocketNotFoundException;
import com.project.luckybocky.pocket.repository.PocketPushRepository;
import com.project.luckybocky.user.entity.User;
import com.project.luckybocky.user.exception.UserNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class PocketPushServiceImpl implements PocketPushService {
	private final PocketPushRepository pocketPushRepository;

	@Override
	public User findPocketOwner(int pocketSeq) {
		Pocket pocket = pocketPushRepository.findByPocketSeq(pocketSeq);

		if (pocket == null){
			log.info("{}의 복주머니를 찾을 수 없습니다.",pocketSeq);
			throw new PocketNotFoundException();
		}


		if (pocket.getUser() == null){
			log.info("{}의 복주머니 주인을 찾을 수 없습니다.",pocketSeq);
			throw new UserNotFoundException();
		}


		return pocket.getUser();
	}
}
