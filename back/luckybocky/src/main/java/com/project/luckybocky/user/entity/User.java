package com.project.luckybocky.user.entity;

import com.project.luckybocky.article.entity.Article;
import com.project.luckybocky.common.BaseEntity;
import com.project.luckybocky.feedback.entity.Feedback;
import com.project.luckybocky.pocket.entity.Pocket;
import com.project.luckybocky.report.entity.Report;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class User extends BaseEntity {
    @Id
    @GeneratedValue
    //12-23 창희 JoinColumn시 칼럼매핑을 하지못해서 명시적으로 추가
    @Column(name ="user_seq")
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

    @OneToMany(mappedBy = "user")
    List<Article> articles = new ArrayList<>();

    @OneToMany(mappedBy = "user")   // cascade 설정 X
    List<Pocket> pockets = new ArrayList<>();


    //12-23 창희 피드백, 신고 칼럼 추가 start
    @OneToMany(mappedBy = "user")
    private List<Feedback> feedbacks = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Report> reports = new ArrayList<>();
    //12-23 창희 피드백, 신고 칼럼 추가 end

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

    public void updateUserInfo(String userNickname, boolean alarmStatus, boolean fortuneVisibility){
        setUserNickname(userNickname);
        setAlarmStatus(alarmStatus);
        setFortuneVisibility(fortuneVisibility);
    }

}