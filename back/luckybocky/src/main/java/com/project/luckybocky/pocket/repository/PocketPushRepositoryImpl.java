package com.project.luckybocky.pocket.repository;

import com.project.luckybocky.pocket.entity.Pocket;
import com.project.luckybocky.user.entity.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class PocketPushRepositoryImpl implements PocketPushRepository{
    private final EntityManager em;
    @Override
    public Pocket findByPocketSeq(int pocketSeq) {
        return em.find(Pocket.class, pocketSeq);
    }


}
