package com.project.luckybocky.pocket.service;

import com.project.luckybocky.common.CommonResponseDto;
import com.project.luckybocky.pocket.repository.PocketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@Transactional
@RequiredArgsConstructor
public class PocketService {
    private final PocketRepository pocketRepository;

    // 입력된 사용자 정보를 바탕으로 복주머니 생성
//    public CommonResponseDto createPocket(){
//
//    }

}
