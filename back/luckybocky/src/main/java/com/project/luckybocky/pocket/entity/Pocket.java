package com.project.luckybocky.pocket.entity;

import com.project.luckybocky.article.entity.Article;
import com.project.luckybocky.common.BaseEntity;
import com.project.luckybocky.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Pocket extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pocket_seq")
    private Integer pocketSeq;

    @ManyToOne
    @JoinColumn(name = "user_seq", nullable = false)
    private User user;

    @Column(length = 255)
    private String pocketAddress;

    //12-23 창희 복주머니에 어떤 게시글이 달렷나
    @OneToMany(mappedBy = "pocket")
    private List<Article> articles = new ArrayList<>();
}
