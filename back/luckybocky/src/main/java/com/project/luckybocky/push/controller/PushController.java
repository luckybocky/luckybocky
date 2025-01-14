package com.project.luckybocky.push.controller;


import com.google.firebase.messaging.FirebaseMessagingException;
import com.project.luckybocky.common.ResponseDto;
import com.project.luckybocky.exam.exception.ExamException;
import com.project.luckybocky.pocket.exception.PocketNotFoundException;
import com.project.luckybocky.push.dto.PushDto;
import com.project.luckybocky.push.service.PushService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1/push" )
//이 클래스가 REST API 컨트롤러임을 나타냅니다.
//반환값은 JSON 형식으로 자동 변환됩니다.
//없으면 반환값을 이름으로하는 뷰를 찾으려고 시도
@RestController
@Slf4j
@RequiredArgsConstructor
@Tag(name = "푸시 알림", description = "푸시 알림")
public class PushController {

    private final PushService pushService;


    @Description("푸시 알림")
    @Operation(
            summary = "푸시 알림 요청",
            description = "복(댓글)과 리복(대댓글) 추가시 푸시 알림을 보낸다"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "푸시 성공"),
            @ApiResponse(responseCode = "401", description = "비회원은 푸시를 보낼수 없음"),
            @ApiResponse(responseCode = "404", description = "컨텐츠를 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = PocketNotFoundException.class))),
            @ApiResponse(responseCode = "500", description = "FCM접근 실패",
                    content = @Content(schema = @Schema(implementation = FirebaseMessagingException.class)))
    })
    @PostMapping
    public ResponseEntity<ResponseDto> pushContent(HttpSession session, @RequestBody PushDto pushDto) throws FirebaseMessagingException {
        String userKey = (String) session.getAttribute("name" );
        if(userKey==null){
            log.info("{}", "비회원은 푸시를 보낼 수 없습니다.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseDto("비회원은 푸시를 지원하지 않습니다." ));
        }

        String fromUser = (String) session.getAttribute("nickname" );
        log.info(" Push Info : {}", pushDto);
        pushService.sendPush(fromUser, pushDto);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("push success" ));
    }



//    @PostMapping("/test")
//    public ResponseEntity<MessageDto> pushComment(HttpSession session, @RequestBody PushDto pushDto) {
//
//        session.setAttribute("user","changhee");
//
//        String toUser = pushDto.getToUser();
//        String fromUser = (String) session.getAttribute("user");
//        String type = pushDto.getType();
//        String url = pushDto.getUrl();
//
//        log.info("toUser : {}, fromUser : {}", toUser,session.getAttribute("user"));
//        try {
//            pushService.sendPush(toUser, fromUser, type,url);
//            return ResponseEntity.status(HttpStatus.OK).body(
//                    MessageDto.builder()
//                            .status("success")
//                            .message("push success")
//                            .build()
//
//            );
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
//                    MessageDto.builder()
//                            .status("fail")
//                            .message("없는 사용자거나 푸시 타입이 잘못되었습니다.")
//                            .build()
//
//            );
//        } catch (FirebaseMessagingException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
//                    body(
//                            MessageDto.builder()
//                                    .status("fail")
//                                    .message("푸시알림 전송에 실패했습니다.")
//                                    .build()
//                    );
//        }
//    }

}


