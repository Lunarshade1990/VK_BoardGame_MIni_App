package com.lunarshade.vkapp.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity(name = "user")
@Getter
@Setter
@RequiredArgsConstructor
public class AppUser {

    @Id
    @GeneratedValue
    private long id;
    @Column(unique = true)
    private String providerId;
    private String adapter;
    private String email;
    private String firstName;
    private String secondName;
    private String teseraProfile;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private Set<Place> places;
    @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL)
    private Set<Event> events;
    @Enumerated(EnumType.STRING)
    @ElementCollection
    private List<Roles> roles;
    @JoinTable(name = "user_plays",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "plays_id"))
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private Set<Play> plays;
    @ManyToMany
    private Set<BoardGame> boardGames;
    private boolean active;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    private City city;
}
