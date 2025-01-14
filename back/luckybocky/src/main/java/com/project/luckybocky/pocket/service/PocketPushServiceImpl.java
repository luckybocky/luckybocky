package com.project.luckybocky.pocket.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.luckybocky.pocket.entity.Pocket;
import com.project.luckybocky.pocket.exception.PocketNotFoundException;
import com.project.luckybocky.pocket.repository.PocketPushRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class PocketPushServiceImpl implements PocketPushService {
	private final PocketPushRepository pocketPushRepository;

	@Override
	public String findPocket(int pocketSeq) {
		Pocket pocket = pocketPushRepository.findByPocketSeq(pocketSeq);

		if (pocket == null)
			throw new PocketNotFoundException();
		return pocket.getUser().getUserKey();
	}
}
