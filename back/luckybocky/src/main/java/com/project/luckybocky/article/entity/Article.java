package com.project.luckybocky.article.entity;

import com.project.luckybocky.common.BaseEntity;
import com.project.luckybocky.feedback.entity.Feedback;
import com.project.luckybocky.fortune.entity.Fortune;
import com.project.luckybocky.pocket.entity.Pocket;
import com.project.luckybocky.report.entity.Report;
import com.project.luckybocky.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Article extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_seq") //12-23 창희 JoinColumn시 칼럼매핑을 하지못해서 명시적으로 추가
    private Integer articleSeq;

    @ManyToOne
    @JoinColumn(name = "user_seq", nullable = false) // 외래키 설정
    private User user;

    @Column(nullable = false)
    private String userNickname;

    //12-23 창희 int -> Fortune으로 변경
    @ManyToOne
    @JoinColumn(name = "fortune_seq", columnDefinition = "smallint", nullable = false)
    private Fortune fortunes;

    @Column(columnDefinition = "tinyint", nullable = false)
    private int articleVisibility;

    @Column(length = 500)
    private String articleContent;

    @Column(length = 300)
    private String articleComment;

    //12-23 창희 신고 칼럼 추가
    @OneToMany(mappedBy = "article")
    private List<Report> reports = new ArrayList<>();

    //12-23 창희 복주머니 칼럼 추가
    @ManyToOne
    @JoinColumn(name = "pocket_seq")
    private Pocket pocket;
}