package com.lunarshade.vkapp.dao.userdao;

import com.lunarshade.vkapp.entity.CollectionType;
import com.lunarshade.vkapp.repository.BoardGameRepository;
import lombok.Data;

@Data
public class CollectionDto {

    public CollectionDto(BoardGameRepository repository, long userId, CollectionType collectionType) {
        minPlayers = repository.getMinPlayersInUserCollection(userId, collectionType);
        maxPlayers = repository.getMaxPlayersInUserCollection(userId, collectionType);
        minTime = repository.getMinTimeInUserCollection(userId, collectionType);
        maxTime = repository.getMaxTimeInUserCollection(userId, collectionType);
    }

    private int minPlayers;
    private int maxPlayers;
    private int minTime;
    private int maxTime;
}
