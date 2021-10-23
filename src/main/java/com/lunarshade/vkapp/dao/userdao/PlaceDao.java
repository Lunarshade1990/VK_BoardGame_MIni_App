package com.lunarshade.vkapp.dao.userdao;

import com.lunarshade.vkapp.entity.Place;
import lombok.Getter;

@Getter
public class PlaceDao {

    public PlaceDao(Place place) {
        this.id = place.getId();
        this.name = place.getName();
        this.address = place.getAddress();
        this.isPublicPlace = place.isPublicPlace();
        this.isHome = place.isHome();
        this.latitude = place.getLatitude();
        this.longitude = place.getLongitude();
        this.isDefault = place.isByDefault();
    }

    private long id;
    private String name;
    private String address;
    private boolean isPublicPlace;
    private boolean isHome;
    private double latitude;
    private double longitude;
    private boolean isDefault;
}
