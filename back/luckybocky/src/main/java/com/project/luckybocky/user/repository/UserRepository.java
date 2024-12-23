package com.project.luckybocky.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.luckybocky.user.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByUserKey(String userKey);
}