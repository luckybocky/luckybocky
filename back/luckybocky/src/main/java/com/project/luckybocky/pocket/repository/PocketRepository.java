package com.project.luckybocky.pocket.repository;

import com.project.luckybocky.pocket.entity.Pocket;
import com.project.luckybocky.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface PocketRepository extends JpaRepository<Pocket, Integer> {
    Optional<Pocket> findPocketByUser(User user);

    Optional<Pocket> findPocketByPocketSeq(int pocketSeq);

    @Query("SELECT p FROM Pocket p WHERE p.user.userKey = :userKey AND FUNCTION('DATE', p.createdAt) >= :startDate AND FUNCTION('DATE', p.createdAt) < :endDate")
    Optional<Pocket> findPocketByUserAndDate(@Param("userKey") String userKey, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    Optional<Pocket> findPocketByPocketAddress(String pocketAddress);

    Optional<Pocket> findFirstByUserOrderByCreatedAtDesc(User user);
}
