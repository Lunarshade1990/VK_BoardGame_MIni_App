package com.lunarshade.vkapp.dto.tesera;

import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;

import java.util.Date;

@Data
public class TeseraGame {

    private Game game;

    @Data
    public static class Game {
        @JsonIgnore
        private Date addedIntoCollection;
        private long teseraId;
        private long bggId;
        private String title;
        private String title2;
        private String description;
        private String photoUrl;
        private int playersMin;
        private int playersMax;
        private int playtimeMin;
        private int playtimeMax;
    }
}
