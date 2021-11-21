package com.lunarshade.vkapp.controller;

import com.lunarshade.vkapp.dao.eventdao.EventDao;
import com.lunarshade.vkapp.dao.eventdao.EventPlayDao;
import com.lunarshade.vkapp.dao.request.gameevent.GameEventRqDto;
import com.lunarshade.vkapp.dao.request.gameevent.PlayRqDto;
import com.lunarshade.vkapp.entity.Event;
import com.lunarshade.vkapp.entity.Play;
import com.lunarshade.vkapp.service.EventService;
import com.lunarshade.vkapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping
@RequiredArgsConstructor
@CrossOrigin
public class GameEventController {

    private final EventService eventService;
    private final UserService userService;

    @PostMapping("/events")
    @ResponseBody
    public EventDao saveNewEvent(@RequestBody GameEventRqDto gameEvent) {
        EventDao eventDao = new EventDao(eventService.saveNewEvent(gameEvent));
        return eventDao;
    }

    @PostMapping("/events/{event}/plays")
    @ResponseBody
    public EventPlayDao addNewPlay(@PathVariable Event event, @RequestBody PlayRqDto playRqDto) {
        return new EventPlayDao(eventService.addNewPlay(event, playRqDto));
    }

    @PostMapping("/events/{event}")
    public void setEventLastUpdateTime(@PathVariable Event event, @RequestBody Date date) {
        eventService.setEventLastUpdateTime(event, date);
    }

    @PostMapping("/plays/{play}/players")
    public void addNewUser(@PathVariable Play play, @RequestBody Long userId) throws Exception {
        eventService.addNewUser(play, userService.find(userId));
    }

    @PostMapping("/plays/{play}/virtuals")
    public void addNewUser(@PathVariable Play play, @RequestBody String info) throws Exception {
        eventService.addNewVirtualUser(play, info);
    }

    @PostMapping("/plays/{play}")
    @ResponseBody
    public EventPlayDao updatePlay(@PathVariable Play play, @RequestBody PlayRqDto playRqDto) throws Exception {
        return new EventPlayDao(eventService.updatePlay(play, playRqDto));
    }

    @PostMapping("/events/{event}/latUpdateDate")
    public void updateLastUpdateDate(@PathVariable Event event, @RequestBody Date date) throws Exception {
        eventService.updateLastUpdateDate(event, date);
    }
}
