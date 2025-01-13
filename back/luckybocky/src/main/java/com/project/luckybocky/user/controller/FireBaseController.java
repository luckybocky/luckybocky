package com.project.luckybocky.user.controller;


import com.project.luckybocky.common.ResponseDto;
import com.project.luckybocky.exam.exception.ExamException;
import com.project.luckybocky.user.dto.FirebaseKeyRequest;
import com.project.luckybocky.user.exception.UserNotFoundException;
import com.project.luckybocky.user.service.UserSettingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Description;
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
@Tag(name = "파이어베이스 키", description = "FCM key 업데이트")
public class FireBaseController {

    private final UserSettingService userSettingService;

    @Description("firebase Key 업데이트")
    @Operation(
            summary = "firebase Key 업데이트 요청",
            description = "FCM에서 발급받은 푸시 키를 업데이트한다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "FCM키 업데이트 성공"),
            @ApiResponse(responseCode = "401", description = "사용자를 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = UserNotFoundException.class)))
    })
    @PutMapping
    public ResponseEntity<ResponseDto> updateFireBaseKey(HttpSession session, @RequestBody FirebaseKeyRequest firebaseKeyRequest) {
        String userKey = (String) session.getAttribute("user");

        String firebaseKey = firebaseKeyRequest.getFirebaseKey();
        log.info("update firebase Key {}", firebaseKeyRequest.getFirebaseKey());
        userSettingService.updateFireBaseKey(userKey, firebaseKey);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("key update successful"));

    }
}
