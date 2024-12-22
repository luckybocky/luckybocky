package com.project.luckybocky.pocket.repository;

import com.project.luckybocky.pocket.dto.NewPocketDto;
import com.project.luckybocky.pocket.entity.Pocket;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PocketRepository {
    private final EntityManager em;

//    public Pocket save(NewPocketDto pocketDto){
//
//    }
}
