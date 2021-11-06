package com.lunarshade.vkapp.dao.request.gameevent;

import java.util.ArrayList;
import java.util.Date;

public record PlayRqDto(Long creator,
                        Long host,
                        Long game,
                        ArrayList<Long> players,
                        Date timeFrom,
                        Date timeTo,
                        Integer playersTo,
                        Integer playersFrom){}
