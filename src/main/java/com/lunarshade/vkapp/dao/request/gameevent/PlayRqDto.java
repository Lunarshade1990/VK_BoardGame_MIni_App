package com.lunarshade.vkapp.dao.request.gameevent;

import java.util.ArrayList;
import java.util.Date;

public record PlayRqDto(
        Long host,
        Long game,
        ArrayList<Long> players,
        Date timeFrom,
        Date timeTo,
        Integer playersTo,
        Integer playersFrom,
        String comment,
        Long tableId,
        ArrayList<String> virtualPlayers){

    public PlayRqDto(Long host,
                     Long game,
                     ArrayList<Long> players,
                     Date timeFrom,
                     Date timeTo,
                     Integer playersTo,
                     Integer playersFrom,
                     String comment,
                     Long tableId,
                     ArrayList<String> virtualPlayers) {
        this.host = host;
        if (game == null)
            throw new IllegalArgumentException("Необходимо выбрать игру");
        else {
            this.game = game;
        }
        this.players = players == null ? new ArrayList<>() : players;
        if (timeFrom == null)
            throw new IllegalArgumentException("Необходимо установить дату начала");
        else
            this.timeFrom = timeFrom;

        if (timeTo == null)
            throw new IllegalArgumentException("Необходимо установить дату окнчания");
        else
            this.timeTo = timeTo;

        if (playersTo == null)
            throw new IllegalArgumentException("Необходимо установить количество игроков");
        else
            this.playersTo = playersTo;

        if (playersFrom == null)
            throw new IllegalArgumentException("Необходимо установить количество игроков");
        else
            this.playersFrom = playersFrom;
        this.comment = comment;
        this.tableId = tableId;
        this.virtualPlayers = virtualPlayers == null ? new ArrayList<>() : virtualPlayers;
    }
}
