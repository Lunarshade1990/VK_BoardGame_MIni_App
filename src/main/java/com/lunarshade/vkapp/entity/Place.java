package com.lunarshade.vkapp.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Table(name = "place")
@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Place {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne()
    private AppUser owner;

    private String address;

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Desk> tables;

    private boolean isPublic;

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL)
    private Set<Event> events;

    private String type;

}