package com.lunarshade.vkapp.dto.tesera;

import lombok.Data;

import java.util.List;

@Data
public class TeseraUserCollectionInfo {

    List<Collection> collections;

    @Data
    private static class Collection {
        private CollectionType collectionType;
        private int gamesTotal;
    }

    public enum CollectionType {
        Own, Played, ToPlay, Top;
    }
}
