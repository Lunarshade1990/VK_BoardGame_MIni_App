package com.lunarshade.vkapp.dao.request.gameevent;

import java.util.ArrayList;

public record GameEventRqDto(
        ArrayList<TableRqDto> tables,
        String type
) {
}
