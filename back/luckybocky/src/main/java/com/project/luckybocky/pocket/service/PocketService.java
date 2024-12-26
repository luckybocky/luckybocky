package com.project.luckybocky.pocket.service;

import com.project.luckybocky.pocket.entity.Pocket;
import com.project.luckybocky.pocket.repository.PocketRepository;
import com.project.luckybocky.user.entity.User;
import com.project.luckybocky.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PocketService {
    private final UserRepository userRepository;
    private final PocketRepository pocketRepository;

    // 입력된 사용자 정보를 바탕으로 복주머니 생성 -> seq만 받아서 생성하게 하고싶은데 뭐임
//    public void createPocket(UserDto userDto){
//        User user = UserDto.findByUserSeq(userDto);
//        Pocket pocket = Pocket.builder().userSeq(user).build();
//    }

    public String getPocketAddress(int userSeq){
        User user = userRepository.findByUserSeq(userSeq)
                .orElseThrow(() -> new IllegalArgumentException("부적절한 사용자입니다."));
        Pocket pocket = pocketRepository.findPocketByUser(user);

        return pocket.getPocketAddress();
    }

}
