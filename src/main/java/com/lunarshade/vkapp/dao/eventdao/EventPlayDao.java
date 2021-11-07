package com.lunarshade.vkapp.dao.eventdao;

import com.lunarshade.vkapp.dao.BoardGameDao;
import com.lunarshade.vkapp.dao.UserDao;
import com.lunarshade.vkapp.entity.Play;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class EventPlayDao {
    private long id;
    private BoardGameDao boardGame;
    private EventTableDao table;
    private int playerMinCount;
    private int playerMaxCount;
    private Calendar startDate;
    private Calendar endDate;
    private String comment;
    private Set<UserDao> players = new HashSet<>();
    Set<String> virtualUsers = new HashSet<>();

    public EventPlayDao(Play play) {
        this.id = play.getId();
        this.boardGame = new BoardGameDao(play.getBoardGame());
        this.table = new EventTableDao(play.getTable());
        this.playerMinCount = play.getPlayerMinCount();
        this.playerMaxCount = play.getPlayerMaxCount();
        this.startDate = play.getPlannedTime().getTimeStart();
        this.endDate = play.getPlannedTime().getTimeEnd();
        this.comment = play.getComment();
        this.players = play.getPlayers()
                .stream()
                .map(UserDao::new)
                .collect(Collectors.toSet());
        this.virtualUsers = play.getVirtualUsers();
    }
}
