package com.lunarshade.vkapp.dao.userdao;

import com.lunarshade.vkapp.entity.DeskShape;

public interface DeskInfo {
    long getId();

    boolean isIsFree();

    int getLength();

    int getMaxPlayersNumber();

    String getName();

    DeskShape getDeskShape();

    int getWidth();
}
