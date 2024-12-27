package com.project.luckybocky.pocket.service;

import com.project.luckybocky.pocket.entity.Pocket;
import com.project.luckybocky.pocket.repository.PocketRepository;
import com.project.luckybocky.user.entity.User;
import com.project.luckybocky.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class PocketService {
    private final UserRepository userRepository;
    private final PocketRepository pocketRepository;

    public String createPocketAddress(int userSeq){
        // uuid로 복주머니 링크 생성 후 저장 & 반환
        UUID uuid = UUID.randomUUID();
        String address = "http://luckybocky.com/" + uuid.toString();

        User user = userRepository.findByUserSeq(userSeq).get();
        Pocket pocket = pocketRepository.findPocketByUser(user);    // 아직 년도 고려 X
        pocket.updateAddress(address);
        pocketRepository.save(pocket);

        return address;
    }

    public String getPocketAddress(int userSeq){
        User user = userRepository.findByUserSeq(userSeq)
                .orElseThrow(() -> new IllegalArgumentException("부적절한 사용자입니다."));
        Pocket pocket = pocketRepository.findPocketByUser(user);

        return pocket.getPocketAddress();
    }
}
