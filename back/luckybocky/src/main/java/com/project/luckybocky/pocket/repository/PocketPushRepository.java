package com.project.luckybocky.pocket.repository;

import com.project.luckybocky.pocket.entity.Pocket;

public interface PocketPushRepository {
    Pocket findByPocketSeq(int pocketSeq);
}
