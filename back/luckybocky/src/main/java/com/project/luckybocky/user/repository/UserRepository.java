package com.project.luckybocky.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.luckybocky.user.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByUserSeq(int userSeq);

	@Query("SELECT u FROM User u WHERE (:key IS NULL AND u.userKey IS NULL) OR (u.userKey = :key)")
	Optional<User> findByUserKey(@Param("key") String userKey);
}
