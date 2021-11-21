package com.lunarshade.vkapp.service;

import com.lunarshade.vkapp.entity.Event;
import com.lunarshade.vkapp.entity.Play;
import com.lunarshade.vkapp.entity.PlayTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
public class EventServiceTest extends Assertions {
    @Autowired
    private EventService eventService;


    @Test
    void setStartAndEndEventDates_TwoPlays() {
        Play play1 = new Play();
        PlayTime playTime1 = new PlayTime();
        playTime1.setTimeStart(new Date(1636981200000L)); // 15.11.2021 13:00
        playTime1.setTimeEnd(new Date(1636984800000L)); // 15.11.2021 14:00
        play1.setPlannedTime(playTime1);

        Play play2 = new Play();
        PlayTime playTime2 = new PlayTime();
        playTime2.setTimeStart(new Date(1636984800000L)); // 15.11.2021 14:00
        playTime2.setTimeEnd(new Date(1636988400000L)); // 15.11.2021 15:00
        play2.setPlannedTime(playTime2);

        Event event = new Event();
        event.getPlays().add(play1);
        event.getPlays().add(play2);

        eventService.setEventStartAndEndDates(event);

        assertAll(
                () -> assertEquals(new Date(1636981200000L), event.getStartDate()),
                () -> assertEquals(new Date(1636988400000L), event.getEndDate())
        );


    }
}
