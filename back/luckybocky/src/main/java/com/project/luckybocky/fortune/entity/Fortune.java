package com.project.luckybocky.fortune.entity;

import com.project.luckybocky.article.entity.Article;
import com.project.luckybocky.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Fortune extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="fortune_seq")
    private Integer fortuneSeq;

    @Column(length = 8, nullable = false)
    private String fortuneName;

    @Column(nullable = false)
    private String fortuneImg;
}
