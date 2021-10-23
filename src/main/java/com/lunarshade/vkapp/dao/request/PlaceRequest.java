package com.lunarshade.vkapp.dao.request;

public record PlaceRequest(
        long userId,
        String name,
        String address,
        double latitude,
        double longitude,
        boolean publicPlace,
        boolean home,
        boolean byDefault
) {
}
