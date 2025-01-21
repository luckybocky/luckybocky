package com.project.luckybocky.qna.entity;

import org.hibernate.annotations.DynamicInsert;

import com.project.luckybocky.common.BaseEntity;
import com.project.luckybocky.qna.dto.QnaUserReqDto;
import com.project.luckybocky.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@DynamicInsert
public class Qna extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer qnaSeq;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String content;

	private String answer;

	@Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
	private Boolean secretStatus = false;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_seq")
	private User user;

	@Column(nullable = false, columnDefinition = "INTEGER DEFAULT 0")
	private Integer hit;

	public static Qna makeQuestion(User user, QnaUserReqDto qnaUserReqDto) {
		return Qna.builder()
			.title(qnaUserReqDto.getTitle())
			.content(qnaUserReqDto.getContent())
			.secretStatus(qnaUserReqDto.getSecretStatus())
			.user(user)
			.build();
	}
}
