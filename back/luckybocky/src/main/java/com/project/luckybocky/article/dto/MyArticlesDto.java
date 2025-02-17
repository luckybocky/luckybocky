package com.project.luckybocky.article.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class MyArticlesDto {

    @Schema(description = "내가 작성한 게시글 목록")
    private List<MyArticleDto> articles;
}
