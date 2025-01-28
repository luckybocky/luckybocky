package com.project.luckybocky.sharearticle.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShareArticleLoginDto{
	boolean isLogin;
	ShareArticleDto shareArticleDto;
}
