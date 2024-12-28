package com.project.luckybocky.feedback.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.project.luckybocky.common.BaseEntity;
import com.project.luckybocky.user.entity.User;

@Entity
@Getter
@Setter
@NoArgsConstructor(access =AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class Feedback extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer feedbackSeq;

    @ManyToOne  //many가 연관관계 주인
    @JoinColumn(name = "user_seq", referencedColumnName = "user_seq")
    private User user;

    private String feedbackContent;

    private byte feedbackRate;
}
