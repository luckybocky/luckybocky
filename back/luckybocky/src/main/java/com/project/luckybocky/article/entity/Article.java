package com.project.luckybocky.article.entity;

import com.project.luckybocky.common.BaseEntity;
import com.project.luckybocky.fortune.entity.Fortune;
import com.project.luckybocky.pocket.entity.Pocket;
import com.project.luckybocky.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Article extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer articleSeq;

    @ManyToOne
    @JoinColumn(name = "user_seq", nullable = false) // 외래키 설정
    private User user;

    @Column(nullable = false)
    private String userNickname;

    @ManyToOne
    @JoinColumn(name = "fortune_seq", columnDefinition = "smallint", nullable = false)
    private Fortune fortune;

    @Column(columnDefinition = "tinyint", nullable = false)
    private int articleVisibility;

    @Column(length = 500)
    private String articleContent;

    @Column(length = 300)
    private String articleComment;

    @ManyToOne
    @JoinColumn(name = "pocket_seq")
    private Pocket pocket;
}