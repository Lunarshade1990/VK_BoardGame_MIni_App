package com.lunarshade.vkapp.dao.tesera;

import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class TeseraUserCollectionInfo {

    private List<Collection> collections;
    private Map<String, Integer> collectionInfo;

    @Data
    private static class Collection {
        private CollectionType collectionType;
        private int gamesTotal;
    }

    public Map<CollectionType, Integer> generateCollectionInfo() {
        return collections.stream()
                .collect(Collectors.toMap(Collection::getCollectionType, Collection::getGamesTotal));
    }

    public enum CollectionType {
        Own, Played, ToPlay, Top;
    }
}
