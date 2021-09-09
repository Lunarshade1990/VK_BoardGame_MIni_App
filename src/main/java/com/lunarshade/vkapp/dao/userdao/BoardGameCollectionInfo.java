package com.lunarshade.vkapp.dao.userdao;

import java.util.Date;

public interface BoardGameCollectionInfo {
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
    Date getAdded();
}
