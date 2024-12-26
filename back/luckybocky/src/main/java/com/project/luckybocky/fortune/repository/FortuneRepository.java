package com.project.luckybocky.fortune.repository;

import com.project.luckybocky.fortune.entity.Fortune;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FortuneRepository extends JpaRepository<Fortune, Integer> {
    Fortune findByFortuneSeq(int fortuneSeq);
}
