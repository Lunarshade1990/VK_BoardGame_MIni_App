package com.lunarshade.vkapp.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
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
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private AppUser creator;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "place_id")
    private Place place;
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<Play> plays = new ArrayList<>();
    private Date lastUpdateTime;
    private Date startDate;
    private Date endDate;
    @Enumerated
    EventType type;

}