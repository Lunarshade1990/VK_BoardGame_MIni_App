package com.lunarshade.vkapp.dao;


import lombok.Data;

@Data
public class CollectionDto {
    private final int minTime;
    private final int maxTime;
    private final int minPlayerNumber;
    private final int maxPlayerNumber;
    private final long totalElements;
}
