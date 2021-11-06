package com.lunarshade.vkapp.controller;

import com.lunarshade.vkapp.dao.request.gameevent.GameEventRqDto;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/events")
@CrossOrigin
@RestController
@Data
public class GameEventController {

    @PostMapping
    @ResponseBody
    public Long saveNewEvent(@RequestBody GameEventRqDto gameEvent) {
        return 0L;
    }
}
