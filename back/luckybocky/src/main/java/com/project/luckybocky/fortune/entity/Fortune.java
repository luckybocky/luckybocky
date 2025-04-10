package com.project.luckybocky.fortune.entity;

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
    @Column(name = "fortune_seq")
    private Short fortuneSeq;

    @Column(length = 8, nullable = false)
    private String fortuneName;
}
