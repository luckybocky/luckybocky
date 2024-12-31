package com.project.luckybocky.pocket.dto;

import com.project.luckybocky.pocket.entity.Pocket;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PocketInfoDto {
    private int pocketSeq;
    private String pocketAddress;
    private int ownerSeq;
    private String ownerKey;
    private String ownerNickname;

    public PocketInfoDto(Pocket pocket){
        this.pocketSeq = pocket.getPocketSeq();
        this.pocketAddress = pocket.getPocketAddress();
        this.ownerSeq = pocket.getUser().getUserSeq();
        this.ownerKey = pocket.getUser().getUserKey();
        this.ownerNickname = pocket.getUser().getUserNickname();
    }
}
