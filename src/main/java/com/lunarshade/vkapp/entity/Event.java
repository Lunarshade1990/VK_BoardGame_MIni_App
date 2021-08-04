package com.lunarshade.vkapp.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Table(name = "event")
@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Event {

    @Id
    @GeneratedValue
    private long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser appUser;
    @ManyToOne
    @JoinColumn(name = "place_id")
    private Place place;
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<Play> plays;

}