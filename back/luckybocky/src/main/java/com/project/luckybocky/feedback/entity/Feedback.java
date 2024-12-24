package com.project.luckybocky.feedback.entity;

import java.time.LocalDateTime;

import com.project.luckybocky.common.BaseEntity;
import com.project.luckybocky.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
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
public class Feedback extends BaseEntity {
	@Id
	@GeneratedValue
	private Integer feedbackSeq;

	@ManyToOne
	@JoinColumn(name = "user_seq", nullable = false)
	private User user;

	@Column(nullable = false)
	private String feedbackContent;

	@Column(nullable = false)
	private Integer feedbackRate;
}
