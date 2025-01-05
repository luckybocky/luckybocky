package com.project.luckybocky.pocket.controller;

import com.project.luckybocky.article.dto.ArticleResponseDto;
import com.project.luckybocky.article.service.ArticleService;
import com.project.luckybocky.common.DataResponseDto;
import com.project.luckybocky.pocket.dto.PocketDto;
import com.project.luckybocky.pocket.dto.PocketInfoDto;
import com.project.luckybocky.pocket.service.PocketService;
import com.project.luckybocky.user.service.UserSettingService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pocket")
@RequiredArgsConstructor
public class PocketController {
    private final UserSettingService userSettingService;
    private final PocketService pocketService;
    private final ArticleService articleService;

    @Description("복주머니 조회")
    @GetMapping
    public ResponseEntity<PocketDto> getAllArticlesInPocket(HttpSession session){
        String userKey = (String) session.getAttribute("user");
        PocketInfoDto pocketInfoDto = pocketService.getPocketInfoByUser(userKey);

        // 상반기면 작년 7/1 ~ 지금 사이에 생성된 복주머니를 조회하도록 함.
//        LocalDate now = LocalDate.now();
//        int year = now.getMonthValue() <= 6 ? now.getYear() - 1 : now.getYear();

        PocketDto pocketDto = new PocketDto();
        pocketDto.setPocketSeq(pocketInfoDto.getPocketSeq());
        pocketDto.setOwner(pocketInfoDto.getOwnerNickname());
        pocketDto.setArticles(articleService.getArticlesByUser(userKey));

        return ResponseEntity.status(HttpStatus.OK).body(pocketDto);
    }

    @Description("복주머니 주소로 복주머니 조회")
    @GetMapping("/{url}")
    public ResponseEntity<PocketDto> getPocket(@PathVariable String url){
        PocketDto pocketDto = new PocketDto();
        PocketInfoDto findPocket = pocketService.getPocketInfo(url);
        pocketDto.setPocketSeq(findPocket.getPocketSeq());  // 누락된 setter 추가
        pocketDto.setOwner(findPocket.getOwnerNickname());
        pocketDto.setOwnerKey(findPocket.getOwnerKey());
        List<ArticleResponseDto> articles = articleService.getArticlesByPocket(findPocket.getPocketSeq());
        pocketDto.setArticles(articles);
        return ResponseEntity.status(HttpStatus.OK).body(pocketDto);
    }

    @Description("복주머니 주소 가져오기")
    @GetMapping("/address")
    public ResponseEntity<DataResponseDto> getPocketAddress(HttpSession session){
        String userKey = (String) session.getAttribute("user");
        String address = pocketService.getPocketAddress(userKey);
        return ResponseEntity.status(HttpStatus.OK).body(new DataResponseDto("복주머니 주소 가져오기 성공", address));
    }

    @Description("복주머니 생성")
    @PostMapping
    public ResponseEntity<DataResponseDto> createPocket(HttpSession session){
        String userKey = (String) session.getAttribute("user");
        String address= pocketService.createPocket(userKey);
        return ResponseEntity.status(HttpStatus.OK).body(new DataResponseDto("복주머니 생성 성공", address));
    }
}
