package com.lunarshade.vkapp.dao.eventdao;

import com.lunarshade.vkapp.entity.Place;
import lombok.Data;


@Data
public class EventPlaceDao {
    private final Long id;
    private final String address;
    private final String type;
    private final double latitude;
    private final double longitude;

    public EventPlaceDao(Place place) {
        this.id = place.getId();
        this.address = place.getAddress();
        this.type = place.getType();
        latitude = place.getLatitude();
        longitude = place.getLongitude();
    }
}
