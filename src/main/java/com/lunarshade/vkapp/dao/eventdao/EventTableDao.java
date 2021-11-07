package com.lunarshade.vkapp.dao.eventdao;

import com.lunarshade.vkapp.entity.Desk;
import com.lunarshade.vkapp.entity.DeskShape;
import com.lunarshade.vkapp.entity.DeskType;

public class EventTableDao {
    private long id;
    private int length;
    private int width;
    private int maxPlayersNumber;
    private DeskShape deskShape;
    private DeskType deskType;

    public EventTableDao(Desk desk) {
        this.id = desk.getId();
        this.length = desk.getLength();
        this.width = desk.getWidth();
        this.maxPlayersNumber = desk.getMaxPlayersNumber();
        this.deskShape = desk.getDeskShape();
        this.deskType = desk.getDeskType();
    }
}
