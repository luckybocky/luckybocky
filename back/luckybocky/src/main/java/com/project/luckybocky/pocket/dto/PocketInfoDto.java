package com.project.luckybocky.pocket.dto;

import com.project.luckybocky.pocket.entity.Pocket;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PocketInfoDto {
    private int pocketSeq;
    private String pocketAddress;
    private int userSeq;
    private String userKey;
    private String userNickname;
    private boolean fortuneVisibility;

    public PocketInfoDto(Pocket pocket){
        this.pocketSeq = pocket.getPocketSeq();
        this.pocketAddress = pocket.getPocketAddress();
        this.userSeq = pocket.getUser().getUserSeq();
        this.userKey = pocket.getUser().getUserKey();
        this.userNickname = pocket.getUser().getUserNickname();
        this.fortuneVisibility = pocket.getUser().isFortuneVisibility();
    }
}
