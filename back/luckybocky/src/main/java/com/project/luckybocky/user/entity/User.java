package com.project.luckybocky.user.entity;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.project.luckybocky.article.entity.Article;
import com.project.luckybocky.common.BaseEntity;
import com.project.luckybocky.feedback.entity.Feedback;
import com.project.luckybocky.pocket.entity.Pocket;
import com.project.luckybocky.report.entity.Report;
import com.project.luckybocky.sharearticle.entity.ShareArticle;
import com.project.luckybocky.user.dto.UserInfoDto;
import com.project.luckybocky.user.dto.UserLoginDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class User extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_seq") //JoinColumn시 칼럼매핑을 하지못해서 명시적으로 추가
	private Integer userSeq;

	@Column(unique = true)
	private String userKey;

	@Column(nullable = true)
	private String userNickname;

	//    @Column(unique = true)
	private String firebaseKey;

	@Column(nullable = false)
	private boolean alarmStatus;

	@Column(nullable = false)
	private boolean fortuneVisibility;

	@Column(nullable = false, columnDefinition = "SMALLINT DEFAULT 0")
	private byte role;

	@OneToMany(mappedBy = "user")
	List<Article> articles = new ArrayList<>();

	//이 공유페이지를 직접생성한 사람이라는뜻
	@OneToMany(mappedBy = "user")
	List<ShareArticle> shareArticles = new ArrayList<>();

	@OneToMany(mappedBy = "user")   // cascade 설정 X
	List<Pocket> pockets = new ArrayList<>();

	//12-23 창희 피드백, 신고 칼럼 추가 start
	@OneToMany(mappedBy = "user")
	private List<Feedback> feedbacks = new ArrayList<>();

	@OneToMany(mappedBy = "reporter")
	private List<Report> reporters; // 내가 신고한 목록

	@OneToMany(mappedBy = "offender")
	private List<Report> offenders; // 내가 신고당한 목록


	@Override
	public String toString() {
		return "User{" +
			"userSeq=" + userSeq +
			", userKey='" + userKey + '\'' +
			", userNickname='" + userNickname + '\'' +
			", firebaseKey='" + firebaseKey + '\'' +
			", alarmStatus=" + alarmStatus +
			", fortuneVisibility=" + fortuneVisibility +
			'}';
	}

	public void updateUserInfo(String userNickname, boolean alarmStatus, boolean fortuneVisibility) {
		setUserNickname(userNickname);
		setAlarmStatus(alarmStatus);
		setFortuneVisibility(fortuneVisibility);
	}



	public UserInfoDto getUserInfo() {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String formattedDate = this.getCreatedAt().format(formatter);

		return UserInfoDto.builder()
			.userNickname(this.getUserNickname())
			.alarmStatus(this.isAlarmStatus())
			.fortuneVisibility(this.isFortuneVisibility())
			.createdAt(formattedDate)
			.build();
	}

	public UserLoginDto getMemberInfo() {
		UserInfoDto userInfoDto = getUserInfo();
		return new UserLoginDto(userInfoDto, true);
	}

	public static UserLoginDto getNonMemberInfo() {
		return new UserLoginDto(null, false);
	}


}