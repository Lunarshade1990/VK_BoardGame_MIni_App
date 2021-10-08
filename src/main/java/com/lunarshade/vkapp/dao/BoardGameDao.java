package com.lunarshade.vkapp.dao;


import com.lunarshade.vkapp.entity.BoardGame;
import com.lunarshade.vkapp.entity.Play;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class BoardGameDao {
    private final long id;
    private final String name;
    private final String description;
    private final String teseraUrl;
    private final String picture;
    private final long teseraId;
    private final long bggId;
    private final int minPlayerNumber;
    private final int maxPlayerNumber;
    private final int minTime;
    private final int maxTime;
    private final List<Long> plays = new ArrayList<>();
    private final Set<BoardGameCollectionDao> boardGameCollections = new HashSet<>();

    public BoardGameDao(BoardGame boardGame) {
        this.id = boardGame.getId();
        this.name = boardGame.getName();
        this.description = boardGame.getDescription();
        this.teseraUrl = boardGame.getTeseraUrl();
        this.picture = boardGame.getPicture();
        this.teseraId = boardGame.getTeseraId();
        this.bggId = boardGame.getBggId();
        this.minPlayerNumber = boardGame.getMinPlayerNumber();
        this.maxPlayerNumber = boardGame.getMaxPlayerNumber();
        this.minTime = boardGame.getMinTime();
        this.maxTime = boardGame.getMaxTime();
        plays.addAll(boardGame.getPlays().stream()
                .map(Play::getId)
                .toList());
        boardGameCollections.addAll(boardGame.getBoardGameCollections()
                .stream()
                .map(BoardGameCollectionDao::new)
                .collect(Collectors.toSet()));
    }
}
