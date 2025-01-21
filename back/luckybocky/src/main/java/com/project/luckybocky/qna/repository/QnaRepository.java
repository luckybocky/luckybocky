package com.project.luckybocky.qna.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.project.luckybocky.qna.entity.Qna;
import com.project.luckybocky.user.entity.User;

public interface QnaRepository extends JpaRepository<Qna, Integer> {
	Page<Qna> findAllByisDeletedIsFalse(Pageable pageable);

	Page<Qna> findAllByisDeletedIsFalseAndUser(Pageable pageable, User user);
}
