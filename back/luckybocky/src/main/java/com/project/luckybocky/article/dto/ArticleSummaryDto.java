package com.project.luckybocky.article.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ArticleSummaryDto {
    private int articleSeq;
    private String fortuneName;
    private int fortuneImg;
}
