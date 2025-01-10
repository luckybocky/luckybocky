package com.project.luckybocky.pocket.dto;

import com.project.luckybocky.article.dto.ArticleSummaryDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PocketDto {
    private int pocketSeq;
    private String userNickname;
    private List<ArticleSummaryDto> articles;
}
