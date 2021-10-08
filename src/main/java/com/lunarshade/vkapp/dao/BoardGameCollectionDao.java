package com.lunarshade.vkapp.dao;

import com.lunarshade.vkapp.entity.BoardGameCollection;
import com.lunarshade.vkapp.entity.CollectionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;


@AllArgsConstructor
@Getter
@Setter
public class BoardGameCollectionDao implements Serializable {

    private final long id;
    private final CollectionType type;
    private final UserDao owner;
    private final Date added;

    public BoardGameCollectionDao(BoardGameCollection boardGameCollection) {
        UserDao userDao = new UserDao(boardGameCollection.getCollection().getAppUser());
        this.id = boardGameCollection.getCollection().getId();
        this.type = boardGameCollection.getCollection().getCollectionType();
        this.owner = userDao;
        this.added = boardGameCollection.getAdded();
    }
}
