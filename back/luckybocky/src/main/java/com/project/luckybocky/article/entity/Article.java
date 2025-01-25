package com.project.luckybocky.article.entity;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.project.luckybocky.article.dto.ArticleResponseDto;
import com.project.luckybocky.article.dto.ArticleSummaryDto;
import com.project.luckybocky.article.dto.MyArticleDto;
import com.project.luckybocky.common.BaseEntity;
import com.project.luckybocky.fortune.entity.Fortune;
import com.project.luckybocky.pocket.entity.Pocket;
import com.project.luckybocky.report.entity.Report;
import com.project.luckybocky.sharearticle.entity.ShareArticle;
import com.project.luckybocky.user.dto.UserInfoDto;
import com.project.luckybocky.user.entity.User;

import jakarta.persistence.*;
import lombok.*;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE article SET is_deleted = true WHERE article_seq = ?")
@Where(clause = "is_deleted = false")
public class Article extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "article_seq") //12-23 창희 JoinColumn시 칼럼매핑을 하지못해서 명시적으로 추가
	private Integer articleSeq;

	//복을 보낸 사용자 (null일 경우 비회원)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_seq") // 외래키 설정
	private User user;

	//복을 보낸 사용자의 닉네임
	@Column(nullable = false)
	private String userNickname;

	//12-23 창희 int -> Fortune으로 변경
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fortune_seq", columnDefinition = "smallint", nullable = false)
	private Fortune fortune;

	@Column(nullable = false)
	private boolean articleVisibility;

	@Column(length = 500)
	private String articleContent;

	@Column(length = 300)
	private String articleComment;

	//12-23 창희 신고 칼럼 추가
	@OneToMany(mappedBy = "article")
	private List<Report> reports = new ArrayList<>();

	@ManyToOne(fetch = FetchType.LAZY)
	private ShareArticle shareArticle;

	//12-23 창희 복주머니 칼럼 추가
	//어떤 복주머니에 달려있는 복인지
	@ManyToOne
	@JoinColumn(name = "pocket_seq")
	private Pocket pocket;

	public void updateComment(String comment) {
		this.articleComment = comment;
	}

	public ArticleSummaryDto summaryArticle() {
    return new ArticleSummaryDto(this.articleSeq, this.getFortune().getFortuneName(), this.fortune.getFortuneSeq(), this.userNickname);
	}

	//=====창희 dto 함수 start

	public MyArticleDto getMyArticleDto() {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		return MyArticleDto
			.builder()
			.pocketOwner(this.getPocket().getUser().getUserNickname())
			.articleOwner(this.getUserNickname())
			.content(this.getArticleContent())
			.comment(this.getArticleComment())
			.fortuneName(this.getFortune().getFortuneName())
			.fortuneImg(this.getFortune().getFortuneSeq())
			.createdAt(this.getCreatedAt().format(formatter))
			.build();
	}
	//=====창희 dto 함수 end


	public ArticleResponseDto toArticleResponseDto() {
		return ArticleResponseDto.builder()
			.articleVisibility(this.articleVisibility)
			.articleSeq(this.articleSeq)
			.userKey(this.user == null ? null : this.user.getUserKey())
			.userNickname(this.userNickname)
			.articleContent(this.articleContent)
			.articleComment(this.articleComment)
			.fortuneName(this.fortune.getFortuneName())
			.fortuneImg(this.fortune.getFortuneSeq())
			.createdAt(this.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
			.build();
	}
}