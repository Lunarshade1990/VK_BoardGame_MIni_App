package com.lunarshade.vkapp.dao.eventdao;

import com.lunarshade.vkapp.entity.Desk;
import com.lunarshade.vkapp.entity.DeskShape;
import com.lunarshade.vkapp.entity.DeskType;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Data
public class EventTableDao {
    private long id;
    private int length;
    private int width;
    private int maxPlayersNumber;
    private DeskShape deskShape;
    private DeskType deskType;
    private Date startTime;
    private Date endTime;

    public EventTableDao(Desk desk) {
        this.id = desk.getId();
        this.length = desk.getLength();
        this.width = desk.getWidth();
        this.maxPlayersNumber = desk.getMaxPlayersNumber();
        this.deskShape = desk.getDeskShape();
        this.deskType = desk.getDeskType();
        setTime(desk);
    }

    public void setTime(Desk desk) {
        List<Date> dates = desk.getPlays().stream()
                .flatMap(play -> {
                    List<Date> d = new ArrayList<>();
                    d.add(play.getPlannedTime().getTimeStart());
                    d.add(play.getPlannedTime().getTimeEnd());
                    return d.stream();})
                .sorted()
                .toList();
        this.startTime = dates.get(0);
        this.endTime = dates.get(dates.size()-1);
    }
}
