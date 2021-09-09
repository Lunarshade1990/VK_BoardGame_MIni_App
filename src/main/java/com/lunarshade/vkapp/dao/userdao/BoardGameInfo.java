package com.lunarshade.vkapp.dao.userdao;

import java.util.Date;
import java.util.Set;

public interface BoardGameInfo {
    long getId();
    String getName();
    String getDescription();
    String getPicture();
    long getTeseraId();
    long getBggId();
    int getMinPlayerNumber();
    int getMaxPlayerNumber();
    int getMinTime();
    int getMaxTime();
    Set<PlayInfo> getPlays();
    Set<BoardGameCollectionInfo> getBoardGameCollections();

    interface BoardGameCollectionInfo {
        CollectionInfo getCollection();
        Date getAdded();
    }

    interface PlayInfo {
        long getId();
    }
}
