package com.project.luckybocky.user.entity;


import com.project.luckybocky.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;


@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue
    private int userSeq;

    @Column(unique = true)
    private String userKey;

    private String userNickname;

//    @Column(unique = true)
    private String firebaseKey;


    private boolean alarmStatus;


    private boolean fortuneVisibility;

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