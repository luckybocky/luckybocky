package com.project.luckybocky.feedback.entity;

import com.project.luckybocky.common.BaseEntity;
import com.project.luckybocky.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.checkerframework.common.value.qual.MinLen;

@Entity
@Getter
@Setter
@NoArgsConstructor(access =AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class Feedback extends BaseEntity {

    @Id
    @GeneratedValue
    private Integer feedbackSeq;

    @ManyToOne  //many가 연관관계 주인
    @JoinColumn(name = "user_seq", referencedColumnName = "user_seq")
    private User user;

    private String feedbackContent;

    private byte feedbackRate;
}
