package com.project.luckybocky.article.entity;

import com.project.luckybocky.common.BaseEntity;
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
    private User userSeq;

    @Column(nullable = false)
    private String userNickname;

    @Column(columnDefinition = "smallint", nullable = false)
    private int fortuneSeq;

    @Column(columnDefinition = "tinyint", nullable = false)
    private int articleVisibility;

    @Column(length = 500)
    private String articleContent;

    @Column(length = 300)
    private String articleComment;
}