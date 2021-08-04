package com.lunarshade.vkapp.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Blob;
import java.util.Set;

@Table(name = "board_game")
@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class BoardGame {

    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String description;
    private String tesersUrl;
    @Lob
    private Blob picture;
    @OneToMany
    private Set<Play> plays;
    @ManyToMany
    private Set<AppUser> appUsers;
    @Embedded
    private BoardGameInfo boardGameInfo;
}
