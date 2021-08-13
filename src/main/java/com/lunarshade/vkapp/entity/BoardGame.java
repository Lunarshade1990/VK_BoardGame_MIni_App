package com.lunarshade.vkapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
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
    private Date addedToCollection;
    private String name;
    private String name2;
    @Column(columnDefinition = "text")
    private String description;
    private String teseraUrl;
    private String picture;
    private long teseraId;
    private long bggId;
    @JsonIgnore
    @OneToMany
    private Set<Play> plays;
    @ManyToMany
    @JsonIgnore
    private Set<AppUser> appUsers;
    @Embedded
    private BoardGameInfo boardGameInfo;
}
