package com.project.luckybocky.pocket.service;

import com.project.luckybocky.pocket.dto.PocketInfoDto;
import com.project.luckybocky.pocket.entity.Pocket;
import com.project.luckybocky.pocket.repository.PocketRepository;
import com.project.luckybocky.user.entity.User;
import com.project.luckybocky.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class PocketService {
    private final UserRepository userRepository;
    private final PocketRepository pocketRepository;

    public String createPocketAddress(String userKey){
        // uuid로 복주머니 링크 생성 후 저장 & 반환
        UUID uuid = UUID.randomUUID();
        String address = uuid.toString();

        User user = userRepository.findByUserKey(userKey).get();
        Pocket pocket = pocketRepository.findPocketByUser(user)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 복주머니입니다."));    // 아직 년도 고려 X
        pocket.updateAddress(address);
        pocketRepository.save(pocket);

        return address;
    }

    public String getPocketAddress(String userKey){
        User user = userRepository.findByUserKey(userKey)
                .orElseThrow(() -> new IllegalArgumentException("부적절한 사용자입니다."));
        Pocket pocket = pocketRepository.findPocketByUser(user)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 복주머니입니다."));;

        return pocket.getPocketAddress();
    }

    public PocketInfoDto getPocketInfo(String address){
        Pocket pocket = pocketRepository.findPocketByPocketAddress(address)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 복주머니입니다."));
        return new PocketInfoDto(pocket);
    }

    public void createPocket(String userKey) {
        User user = userRepository.findByUserKey(userKey)
                .orElseThrow(() -> new IllegalArgumentException("부적절한 사용자입니다."));
        Pocket pocket = Pocket.builder().user(user).build();
        pocketRepository.save(pocket);
    }

    public PocketInfoDto getPocketInfoByUser(String userKey){
        User user = userRepository.findByUserKey(userKey)
                .orElseThrow(() -> new IllegalArgumentException("부적절한 사용자입니다."));
        Pocket pocket = pocketRepository.findFirstByUserOrderByCreatedAtDesc(user)
                .orElseThrow(() -> new IllegalArgumentException("복주머니가 존재하지 않습니다."));
        return new PocketInfoDto(pocket);
    }
}
