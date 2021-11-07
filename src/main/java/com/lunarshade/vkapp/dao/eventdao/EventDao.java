package com.lunarshade.vkapp.dao.eventdao;

import com.lunarshade.vkapp.entity.Event;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

public class EventDao {
    private final Long id;
    private final Long creator;
    private final EventPlaceDao place;
    private final List<EventPlayDao> plays;
    private final Calendar lastUpdateTime;


    public EventDao(Event event) {
        this.id = event.getId();
        this.creator = event.getCreator().getId();
        this.place = new EventPlaceDao(event.getPlace());
        this.plays = event.getPlays()
                .stream()
                .map(EventPlayDao::new)
                .collect(Collectors.toList());
        this.lastUpdateTime = event.getLastUpdateTime();
    }
}
