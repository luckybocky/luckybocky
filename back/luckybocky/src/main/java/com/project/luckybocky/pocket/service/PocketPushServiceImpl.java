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

		if (pocket == null)
			throw new PocketNotFoundException();

		if (pocket.getUser() == null)
			throw new UserNotFoundException();

		return pocket.getUser();
	}
}
