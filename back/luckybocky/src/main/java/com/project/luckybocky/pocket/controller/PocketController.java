package com.project.luckybocky.pocket.controller;

import com.project.luckybocky.article.service.ArticleService;
import com.project.luckybocky.pocket.dto.PocketAddressDto;
import com.project.luckybocky.pocket.dto.PocketDto;
import com.project.luckybocky.pocket.service.PocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/pocket")
@RequiredArgsConstructor
public class PocketController {

    private final PocketService pocketService;
    private final ArticleService articleService;

    @Description("복주머니와 달린 복들을 가져온다.")
    @GetMapping
    public ResponseEntity<PocketDto> getAllArticlesInPocket(@RequestParam int userSeq){
        // 상반기면 작년 7/1 ~ 지금 사이에 생성된 복주머니를 조회하도록 함.
        LocalDate now = LocalDate.now();
        int year = now.getMonthValue() <= 6 ? now.getYear() - 1 : now.getYear();

        PocketDto pocketDto = new PocketDto();
        pocketDto.setArticles(articleService.getAllArticlesByDate(userSeq, year));

        return ResponseEntity.status(HttpStatus.OK).body(pocketDto);
    }


    @Description("사용자 복주머니 주소(url)를 가져온다.")
    @GetMapping("/address")
    public ResponseEntity<PocketAddressDto> getPocketAddress(@RequestParam int userSeq){
        // ** userSeq는 나중에 세션에서 가져와서 처리
        String address = pocketService.getPocketAddress(userSeq);
        PocketAddressDto pocketAddressDto = new PocketAddressDto();

        if (!address.isEmpty()){
            pocketAddressDto.setAddress(address);
            return ResponseEntity.status(HttpStatus.OK).body(pocketAddressDto);
        } else {    // 주소가 없을 경우 (빈값, length = 0)
            address = pocketService.createPocketAddress(userSeq);
            pocketAddressDto.setAddress(address);
            return ResponseEntity.status(HttpStatus.OK).body(pocketAddressDto);
        }
    }

    
}
