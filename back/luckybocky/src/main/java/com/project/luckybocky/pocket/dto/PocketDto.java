package com.project.luckybocky.pocket.dto;

import com.project.luckybocky.article.dto.ArticleResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PocketDto {
//    public int year = 2025;     // ** 아직 년도 별 구현 X
    public List<ArticleResponseDto> articles;     // ** 이름을 pocket으로 하면 헷갈리지 않을까요?
}
