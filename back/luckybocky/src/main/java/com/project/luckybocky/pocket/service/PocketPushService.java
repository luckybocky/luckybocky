package com.project.luckybocky.pocket.service;

import com.project.luckybocky.user.entity.User;

public interface PocketPushService {
    User findPocketOwner(int pocketSeq);
}
