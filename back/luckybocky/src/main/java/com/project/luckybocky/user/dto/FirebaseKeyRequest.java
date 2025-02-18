package com.project.luckybocky.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FirebaseKeyRequest {
    @Schema(description = "푸시알림을 보내기 위한 파이어베이스키")
    private String firebaseKey;
}
