package com.lunarshade.vkapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Table(name = "board_game")
@Entity
@Setter
@RequiredArgsConstructor
@Getter
public class BoardGame {

    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String name2;
    @Column(columnDefinition = "text")
    private String description;
    private String teseraUrl;
    private String picture;
    private long teseraId;
    private long bggId;
    private int minPlayerNumber;
    private int maxPlayerNumber;
    private int minTime;
    private int maxTime;

    @JsonIgnore
    @OneToMany
    private Set<Play> plays = new HashSet<>();

    @OneToMany(mappedBy = "boardGame", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private Set<BoardGameCollection> boardGameCollections = new HashSet<>();

    public Date getAdded(CollectionType type, long userId) {
        BoardGameCollection collection = this.getBoardGameCollections()
                .stream()
                .filter(c -> c.getCollection().getCollectionType() == type && c.getCollection().getAppUser().getId() == userId)
                .findFirst()
                .orElseThrow();
        return collection.getAdded();
    }
}
