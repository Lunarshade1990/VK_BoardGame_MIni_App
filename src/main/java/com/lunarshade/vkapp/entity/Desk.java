package com.lunarshade.vkapp.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Desk {

    @Id
    @GeneratedValue
    private long id;
    private String name;
    private int length;
    private int width;
    private int maxPlayersNumber;
    private boolean isFree;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private Place place;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private List<Play> plays;
    @Enumerated
    private DeskShape deskShape;
}
