package com.project.luckybocky.pocket.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.project.luckybocky.article.entity.Article;
import com.project.luckybocky.common.BaseEntity;
import com.project.luckybocky.pocket.dto.PocketDto;
import com.project.luckybocky.pocket.dto.PocketInfoDto;
import com.project.luckybocky.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Pocket extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pocket_seq")
	private Integer pocketSeq;

	@ManyToOne
	@JoinColumn(name = "user_seq", nullable = false, unique = true)
	private User user;

	@Column(length = 255)
	private String pocketAddress;

	//12-23 창희 복주머니에 어떤 게시글이 달렷나
	@OneToMany(mappedBy = "pocket")
	private List<Article> articles = new ArrayList<>();

	public PocketInfoDto pocketInfo() {
		return new PocketInfoDto(this.pocketSeq, this.pocketAddress, this.getUser().getUserSeq(),
			this.getUser().getUserKey(), this.getUser().getUserNickname(), this.getUser().isFortuneVisibility());
	}

	public PocketDto pocket() {
		return PocketDto.builder()
			.pocketSeq(this.pocketSeq)
			.userNickname(this.user.getUserNickname())
			.fortuneVisibility(this.getUser().isFortuneVisibility())
			.articles(this.articles.stream()
				.filter(
					article -> !article.isDeleted()
				)
				.map(Article::summaryArticle)
				.collect(Collectors.toList()))
			.build();
	}
	// Article의 @Where(clause = "is_deleted = false")로 인해 admin 시스템 조회시 조건 충돌 발생하여 위와 같이 변경(우재)

	//    public void updateAddress(String pocketAddress){
	//        this.pocketAddress = pocketAddress;
	//    }

	//연관관계 편의 메소드
	public void addArticle(Article article) {
		articles.add(article);
		article.setPocket(this);
	}
}
