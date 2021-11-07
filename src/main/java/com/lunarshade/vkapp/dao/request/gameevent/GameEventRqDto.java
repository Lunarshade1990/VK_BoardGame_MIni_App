package com.lunarshade.vkapp.dao.request.gameevent;

import java.util.ArrayList;

public record GameEventRqDto(
        Long creator,
        Long place,
        ArrayList<TableRqDto> tables,
        String type
) {
}
