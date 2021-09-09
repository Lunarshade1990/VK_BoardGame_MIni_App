package com.lunarshade.vkapp.dao.userdao;

import com.lunarshade.vkapp.entity.CollectionType;
import com.lunarshade.vkapp.entity.Roles;

import java.util.List;
import java.util.Set;

public interface UserInfo {
    long getId();

    String getAvatarUrl();

    String getProviderId();

    List<Roles> getRoles();

    String getSecondName();

    String getTeseraProfile();

    CityInfo getCity();

    Set<CollectionInfo> getCollections();

    Set<EventInfo> getEvents();

    Set<PlaceInfo> getPlaces();

    Set<PlayInfo> getPlays();

    interface CityInfo {
        String getName();
    }

    interface CollectionInfo {
        long getId();
        CollectionType getCollectionType();
    }

    interface EventInfo {
        long getId();
    }

    interface PlaceInfo {
        long getId();
    }

    interface PlayInfo {
        long getId();
    }
}
