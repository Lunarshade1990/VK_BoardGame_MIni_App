package com.lunarshade.vkapp.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;


@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "board_game_collection")
@IdClass(BoardGameCollectionId.class)
public class BoardGameCollection implements Serializable {

    @Id
    @JoinColumn(name = "board_game_id")
    @ManyToOne
    BoardGame boardGame;

    @Id
    @JoinColumn(name = "collection_id")
    @ManyToOne
    Collection collection;

    Date added;

    Date deleted;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoardGameCollection that = (BoardGameCollection) o;
        return Objects.equals(boardGame, that.boardGame) && Objects.equals(collection, that.collection);
    }

    @Override
    public int hashCode() {
        return Objects.hash(boardGame, collection);
    }
}
