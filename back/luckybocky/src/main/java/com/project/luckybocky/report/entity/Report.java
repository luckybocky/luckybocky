package com.project.luckybocky.report.entity;

import com.project.luckybocky.article.entity.Article;
import com.project.luckybocky.common.BaseEntity;
import com.project.luckybocky.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Report extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer reportSeq;

    @ManyToOne
    @JoinColumn(name = "article_seq", referencedColumnName = "article_seq")
    private Article article;

    @ManyToOne
    @JoinColumn(name = "reporter_seq", referencedColumnName = "user_seq")
    private User reporter;  // 게시글 신고자

    @ManyToOne
    @JoinColumn(name = "offender_seq", referencedColumnName = "user_seq")
    private User offender;  // 신고당한 게시글 주인

    private byte reportType;

    private String reportContent;

}
