package com.lunarshade.vkapp.dao.request.gameevent;

import java.util.ArrayList;

public record TableRqDto(String type,
                         ArrayList<PlayRqDto> plays,
                         Long id) {
}
