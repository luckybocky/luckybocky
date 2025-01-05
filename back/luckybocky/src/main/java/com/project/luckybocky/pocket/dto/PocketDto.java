package com.project.luckybocky.pocket.dto;

import com.project.luckybocky.article.dto.ArticleResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PocketDto {
//    public int year = 2025;     // ** 아직 년도 별 구현 X
    private int pocketSeq;
    private String user;
    private String userKey;
    private List<ArticleResponseDto> articles;
}
