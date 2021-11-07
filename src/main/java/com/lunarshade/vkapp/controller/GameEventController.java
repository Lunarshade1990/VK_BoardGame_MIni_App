package com.lunarshade.vkapp.controller;

import com.lunarshade.vkapp.dao.request.gameevent.GameEventRqDto;
import com.lunarshade.vkapp.dao.request.gameevent.PlayRqDto;
import com.lunarshade.vkapp.entity.Event;
import com.lunarshade.vkapp.entity.Play;
import com.lunarshade.vkapp.service.EventService;
import com.lunarshade.vkapp.service.UserService;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;

@RequestMapping
@CrossOrigin
@RestController
@Data
public class GameEventController {

    private final EventService eventService;
    private final UserService userService;

    @PostMapping("/events")
    @ResponseBody
    public Event saveNewEvent(@RequestBody GameEventRqDto gameEvent) {
        return eventService.saveNewEvent(gameEvent);
    }

    @PostMapping("/events/{event}/plays")
    @ResponseBody
    public Play addNewPlay(@PathVariable Event event, PlayRqDto playRqDto) {
        return eventService.addNewPlay(event, playRqDto);
    }

    @PostMapping("/events/{event}")
    public void setEventLastUpdateTime(@PathVariable Event event, Calendar date) {
        eventService.setEventLastUpdateTime(event, date);
    }

    @PostMapping("/plays/{play}")
    public void addNewUser(@PathVariable Play play, Long userId) throws Exception {
        eventService.addNewUser(play, userService.find(userId));
    }

    @PostMapping("/plays/{play}")
    public void addNewUser(@PathVariable Play play, String info) throws Exception {
        eventService.addNewVirtualUser(play, info);
    }

    @PostMapping("/plays/{play}")
    public void updatePlay(@PathVariable Play play, PlayRqDto playRqDto) throws Exception {
        eventService.updatePlay(play, playRqDto);
    }
}
