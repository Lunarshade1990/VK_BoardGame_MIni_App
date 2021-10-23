package com.lunarshade.vkapp.dao.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PlaceForm {
    private long userId;
    private String name;
    private String address;
    private double latitude;
    private double longitude;
    private boolean publicPlace;
    private boolean home;
    private boolean byDefault;
}
