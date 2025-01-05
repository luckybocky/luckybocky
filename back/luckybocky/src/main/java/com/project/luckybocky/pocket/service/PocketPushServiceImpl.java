package com.project.luckybocky.pocket.service;

import com.project.luckybocky.pocket.dto.PocketInfoDto;
import com.project.luckybocky.pocket.entity.Pocket;
import com.project.luckybocky.pocket.exception.PocketNotFoundException;
import com.project.luckybocky.pocket.repository.PocketPushRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class PocketPushServiceImpl implements PocketPushService{
    private final PocketPushRepository pocketPushRepository;

    @Override
    public String findPocket(int pocketSeq) {
        Pocket pocket = pocketPushRepository.findByPocketSeq(pocketSeq);

        if(pocket == null) throw new PocketNotFoundException("not found pocket");
        return pocket.getUser().getUserKey();
    }
}
