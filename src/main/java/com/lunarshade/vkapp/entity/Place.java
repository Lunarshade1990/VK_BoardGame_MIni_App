package com.lunarshade.vkapp.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
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

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    private AppUser owner;


    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private AppUser creator;

    private String name;

    private String address;

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Desk> tables = new HashSet<>();

    @Column(columnDefinition = "boolean default false")
    private boolean publicPlace;

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Event> events = new HashSet<>();

    private String type;

    @Column(columnDefinition = "boolean default false")
    private boolean home;

    private double latitude;

    private double longitude;

    @Column(columnDefinition = "boolean default false")
    private boolean byDefault;
}