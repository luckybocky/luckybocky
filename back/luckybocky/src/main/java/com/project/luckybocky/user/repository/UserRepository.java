package com.project.luckybocky.user.repository;

import com.project.luckybocky.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUserSeq(int userSeq);

    Optional<User> findByUserKey(String userKey);
}