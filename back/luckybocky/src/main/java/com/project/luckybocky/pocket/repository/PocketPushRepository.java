package com.project.luckybocky.pocket.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.luckybocky.pocket.entity.Pocket;

public interface PocketPushRepository extends JpaRepository<Pocket,Integer> {
    Pocket findByPocketSeq(int pocketSeq);
}
