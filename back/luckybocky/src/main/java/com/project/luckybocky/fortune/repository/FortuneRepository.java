package com.project.luckybocky.fortune.repository;

import com.project.luckybocky.fortune.entity.Fortune;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FortuneRepository extends JpaRepository<Fortune, Integer> {
    Optional<Fortune> findByFortuneSeq(int fortuneSeq);
}
