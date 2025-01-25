package com.project.luckybocky.sharearticle.entity;

import java.util.ArrayList;
import java.util.List;

import com.project.luckybocky.article.dto.ArticleResponseDto;
import com.project.luckybocky.article.entity.Article;
import com.project.luckybocky.common.BaseEntity;
import com.project.luckybocky.fortune.entity.Fortune;
import com.project.luckybocky.sharearticle.dto.ShareArticleDto;
import com.project.luckybocky.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder

/*
* 공유게시글(ShareArticle)
* 유저가 공유메시지를 만들때 필요한 엔티티
* 유저가 복 종류, 내용을 입력해서 새해인사메시지를 만들면 url생성 이걸 사람들한테 공유한다.
* 공유 url로 들어온 사용자가 비회원이면 로그인을 해야한다
* 회원이면 복주머니에 복을 넣는처럼 동작한다(ShareArticle을 Article로 변환 후 저장)
*
* <저장 시 제약조건>
* 만약 이미 저장된 url로 또 다시 들어온다면 저장을 시키지 않아야한다.
* 본인이 쓴 메시지는 본인이 추가할 수 없다 - 주인 체크
* */
public class ShareArticle extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer shareArticleSeq;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_seq") // 외래키 설정
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fortune_seq", columnDefinition = "smallint", nullable = false)
	private Fortune fortune;

	@OneToMany(mappedBy = "shareArticle")
	private List<Article> articles = new ArrayList<>();

	@Column(length = 500)
	private String shareArticleContent;

	@Column(length = 255)
	private String shareArticleAddress;


	public ShareArticle(User user, Fortune fortune, String content, String address) {
		this.user=user;
		this.fortune = fortune;
		this.shareArticleContent=content;
		this.shareArticleAddress=address;

	}




	public ShareArticleDto toShareArticleDto(){
		List<ArticleResponseDto> articleResponseDtos=new ArrayList<>();

		for(Article article : this.articles){
			articleResponseDtos.add(article.toArticleResponseDto());
		}

		return ShareArticleDto
			.builder()
			.shareArticleSeq(this.shareArticleSeq)
			.userKey(this.user.getUserKey())
			.articles(articleResponseDtos)
			.shareArticleContent(this.shareArticleContent)
			.shareArticleAddress(this.shareArticleAddress)
			.shareCount(this.articles.size())
			.build();
	}

}
