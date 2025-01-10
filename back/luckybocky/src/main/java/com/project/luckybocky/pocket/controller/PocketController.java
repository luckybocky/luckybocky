package com.project.luckybocky.pocket.controller;

import com.project.luckybocky.article.service.ArticleService;
import com.project.luckybocky.common.DataResponseDto;
import com.project.luckybocky.pocket.dto.PocketDto;
import com.project.luckybocky.pocket.dto.PocketInfoDto;
import com.project.luckybocky.pocket.service.PocketService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/pocket")
@RequiredArgsConstructor
public class PocketController {
    private final PocketService pocketService;
    private final ArticleService articleService;

//    @Description("나의 복주머니 조회")
//    @GetMapping
//    public ResponseEntity<DataResponseDto<PocketDto>> getAllArticlesInPocket(HttpSession session){
//        String userKey = (String) session.getAttribute("user");
//        PocketInfoDto pocketInfoDto = pocketService.getPocketInfoByUser(userKey);
//
//        PocketDto pocketDto = new PocketDto();
//        pocketDto.setPocketSeq(pocketInfoDto.getPocketSeq());
//        pocketDto.setUserNickname(pocketInfoDto.getUserNickname());
//        pocketDto.setArticles(articleService.getArticlesByUser(userKey));
//
//        return ResponseEntity.status(HttpStatus.OK).body(new DataResponseDto<>("success: getting pocket by user", pocketDto));
//    }

    @Description("복주머니 주소로 복주머니 조회")
    @GetMapping("/{url}")
    public ResponseEntity<DataResponseDto<PocketDto>> getPocket(HttpSession session, @PathVariable String url) {
        String userKey = (String) session.getAttribute("user");

        PocketDto pocketDto = new PocketDto();
        PocketInfoDto findPocket = pocketService.getPocketInfo(url);
        pocketDto.setPocketSeq(findPocket.getPocketSeq());
        pocketDto.setUserNickname(findPocket.getUserNickname());

//        if (userKey == findPocket.getUserKey()){    // 복주머니 주인의 경우 (모든 게시글을 볼 수 있어야 함)
        pocketDto.setArticles(articleService.getAllArticles(findPocket.getPocketSeq()));
//        } else {        // 복주머니의 주인이 아닐경우
//            if (!findPocket.isFortuneVisibility()){     // 비공개 복주머니일 경우 -> 전부 비공개로 설정
//                pocketDto.setArticles(articleService.getAllArticlesInvisible(findPocket.getPocketSeq()));
//            } else {    // 비공개 복주머니가 아닐 경우 -> 비공개 글만 비공개로 설정
//                pocketDto.setArticles(articleService.getAllArticlesCheck(findPocket.getPocketSeq()));
//            }
//        }

        return ResponseEntity.status(HttpStatus.OK).body(new DataResponseDto<>("success: get pocket", pocketDto));
    }

    @Description("복주머니 주소 가져오기")
    @GetMapping("/address")
    public ResponseEntity<DataResponseDto> getPocketAddress(HttpSession session) {
        String userKey = (String) session.getAttribute("user");
        String address = pocketService.getPocketAddress(userKey);
        return ResponseEntity.status(HttpStatus.OK).body(new DataResponseDto("복주머니 주소 가져오기 성공", address));
    }

    @Description("복주머니 생성")
    @PostMapping
    public ResponseEntity<DataResponseDto> createPocket(HttpSession session) {
        String userKey = (String) session.getAttribute("user");
        String address = pocketService.createPocket(userKey);
        return ResponseEntity.status(HttpStatus.OK).body(new DataResponseDto("복주머니 생성 성공", address));
    }
}
