package com.lunarshade.vkapp.dao.eventdao;

import com.lunarshade.vkapp.dao.UserDao;
import com.lunarshade.vkapp.entity.Event;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Data
public class EventDao {
    private final Long id;
    private final UserDao creator;
    private final EventPlaceDao place;
    private final List<EventPlayDao> plays;
    private final Date lastUpdateTime;
    private final Date startDate;
    private final Date endDate;


    public EventDao(Event event) {
        this.id = event.getId();
        this.creator = new UserDao(event.getCreator());
        this.place = new EventPlaceDao(event.getPlace());
        this.plays = event.getPlays()
                .stream()
                .map(EventPlayDao::new)
                .collect(Collectors.toList());
        startDate = event.getStartDate();
        endDate = event.getEndDate();
        this.lastUpdateTime = event.getLastUpdateTime();
    }
}
