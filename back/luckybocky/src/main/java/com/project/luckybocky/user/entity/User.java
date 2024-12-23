package com.project.luckybocky.user.entity;

import com.project.luckybocky.article.entity.Article;
import com.project.luckybocky.common.BaseEntity;
import com.project.luckybocky.pocket.entity.Pocket;
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
    private Integer userSeq;

    @Column(unique = true)
    private String userKey;

    @Column(nullable = false)
    private String userNickname;

//    @Column(unique = true)
    private String firebaseKey;

    @Column(nullable = false)
    private boolean alarmStatus;

    @Column(nullable = false)
    private boolean fortuneVisibility;

    @OneToMany(mappedBy = "user")
    private List<Article> articles = new ArrayList<>();

    @OneToMany(mappedBy = "user")   // cascade 설정 X
    private List<Pocket> pockets = new ArrayList<>();

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
}