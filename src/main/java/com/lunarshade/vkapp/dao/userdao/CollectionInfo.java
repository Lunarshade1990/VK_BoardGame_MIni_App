package com.lunarshade.vkapp.dao.userdao;

import com.lunarshade.vkapp.entity.CollectionType;

public interface CollectionInfo {
    long getId();

    CollectionType getCollectionType();

    AppUserInfo getAppUser();

    interface AppUserInfo {
        long getId();
        String getAvatarUrl();
    }
}
