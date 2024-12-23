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

    @ManyToOne   // 1:1 아닌가요? 만약에 1:N면 사용자마다 복주머니가 여러개라는 뜻인데, 이게 년도별이면 뭐 ㅇㅋ. 근데 그럼 년도 넣는 칸도 있어야하는게 아닌지
    @JoinColumn(name = "user_seq", nullable = false)
    private User user;

    @Column(length = 255)
    private String pocketAddress;

    //12-23 창희 복주머니에 어떤 게시글이 달렷나
    @OneToMany(mappedBy = "pocket")
    private List<Article> articles = new ArrayList<>();

}
