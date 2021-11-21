package com.lunarshade.vkapp.dao.eventdao;

import com.lunarshade.vkapp.dao.BoardGameDao;
import com.lunarshade.vkapp.dao.UserDao;
import com.lunarshade.vkapp.entity.Play;
import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


@Data
public class EventPlayDao {
    private long id;
    private final Long hostId;
    private BoardGameDao boardGame;
    private EventTableDao table;
    private int playerMinCount;
    private int playerMaxCount;
    private Date startDate;
    private Date endDate;
    private String comment;
    private Set<UserDao> players = new HashSet<>();
    Set<String> virtualUsers = new HashSet<>();

    public EventPlayDao(Play play) {
        this.id = play.getId();
        this.boardGame = new BoardGameDao(play.getBoardGame());
        this.hostId = play.getHost().getId();
        this.playerMinCount = play.getPlayerMinCount();
        this.playerMaxCount = play.getPlayerMaxCount();
        this.startDate = play.getPlannedTime().getTimeStart();
        this.endDate = play.getPlannedTime().getTimeEnd();
        this.table = new EventTableDao(play.getTable());
        this.comment = play.getComment();
        this.players = play.getPlayers()
                .stream()
                .map(UserDao::new)
                .collect(Collectors.toSet());
        this.virtualUsers = play.getVirtualUsers();
    }
}
