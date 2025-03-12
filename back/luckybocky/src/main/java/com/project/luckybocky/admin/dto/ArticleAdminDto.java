package com.project.luckybocky.admin.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleAdminDto {
	private Integer articleSeq;
	private String articleContent;
	private Short fortuneSeq;
	private Integer userSeq;
	private String userNickname;
	private Integer pocketSeq;
	private Integer shareArticleSeq;
	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;
	private Boolean isDeleted;
}
