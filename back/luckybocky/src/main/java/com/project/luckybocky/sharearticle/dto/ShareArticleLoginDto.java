package com.project.luckybocky.sharearticle.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
	@Schema(description = "로그인 여부")
	boolean isLogin;

	@Schema(description = "공유 게시글 정보")
	ShareArticleDto shareArticleDto;
}
