package com.lunarshade.vkapp.dao.eventdao;

import com.lunarshade.vkapp.entity.Play;

import java.util.Date;

public class EventTablePlayTime {
    Long playId;
    Long gameId;
    String game;
    Date startTime;
    Date endTime;

    public EventTablePlayTime(Play play) {
        this.playId = play.getId();
        this.gameId = play.getBoardGame().getId();
        this.game = play.getBoardGame().getName();
        this.startTime = play.getPlannedTime().getTimeStart();
        this.endTime = play.getPlannedTime().getTimeEnd();
    }
}
