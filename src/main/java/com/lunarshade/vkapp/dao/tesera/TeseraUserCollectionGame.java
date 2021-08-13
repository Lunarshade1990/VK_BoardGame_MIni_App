package com.lunarshade.vkapp.dao.tesera;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class TeseraUserCollectionGame {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date creationDateUtc;
    private Game game;


    @Data
    public static class Game {
        private long teseraId;
    }
}
