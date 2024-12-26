package com.project.luckybocky.user.controller;


import com.project.luckybocky.common.MessageDto;
import com.project.luckybocky.user.dto.FirebaseKeyRequest;
import com.project.luckybocky.user.service.UserSettingService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/v1/auth/firebase")
@RequiredArgsConstructor
@Slf4j
public class FireBaseController {

    private final UserSettingService userSettingService;

    @PutMapping
    public ResponseEntity<MessageDto> updateFireBaseKey(HttpSession session, @RequestBody FirebaseKeyRequest firebaseKeyRequest) {
        String userKey = (String) session.getAttribute("user");

        String firebaseKey = firebaseKeyRequest.getFirebaseKey();
        log.info("update firebase Key {}", firebaseKeyRequest.getFirebaseKey());
        boolean isChange = userSettingService.updateFireBaseKey(userKey, firebaseKey);
        if (isChange) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(
                            MessageDto
                                    .builder()
                                    .status("success")
                                    .message("key update successful")
                                    .build()
                    );

        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(MessageDto
                            .builder()
                            .status("error")
                            .message("Unauthorized")
                            .build()
                    );

        }
    }
}
