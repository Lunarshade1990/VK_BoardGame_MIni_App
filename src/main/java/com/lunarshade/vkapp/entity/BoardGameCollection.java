package com.lunarshade.vkapp.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "board_game_collection")
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
}
