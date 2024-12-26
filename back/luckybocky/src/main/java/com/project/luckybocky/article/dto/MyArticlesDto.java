package com.project.luckybocky.article.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class MyArticlesDto {
    private List<MyArticleDto> articles;
}
