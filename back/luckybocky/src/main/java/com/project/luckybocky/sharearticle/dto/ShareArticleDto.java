package com.project.luckybocky.sharearticle.dto;

import java.util.ArrayList;
import java.util.List;

import com.project.luckybocky.article.dto.ArticleResponseDto;
import com.project.luckybocky.article.entity.Article;
import com.project.luckybocky.fortune.entity.Fortune;
import com.project.luckybocky.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShareArticleDto {
	private Integer shareArticleSeq;

	private String userKey;

	private int fortuneSeq;

	private List<ArticleResponseDto> articles;

	private String shareArticleContent;

	private String shareArticleAddress;

	private Integer shareCount;

}
