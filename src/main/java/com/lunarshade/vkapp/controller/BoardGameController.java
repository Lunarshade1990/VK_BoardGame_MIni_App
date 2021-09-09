package com.lunarshade.vkapp.controller;

import com.lunarshade.vkapp.repository.BoardGameRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/boardgames")
@CrossOrigin
public class BoardGameController {
    private final BoardGameRepository boardGameRepository;

    public BoardGameController(BoardGameRepository boardGameRepository) {
        this.boardGameRepository = boardGameRepository;
    }

//    @GetMapping
//    @ResponseBody
//    public HttpEntity<Void> getBoardGamesByUser (long userId) {
//
//    }

//    @GetMapping
//    @ResponseBody
//    public List<BoardGameDao> getBoardGamesByUserIdWithFilters(
//            @RequestParam long userId,
//            @RequestParam(required = false) Integer minPlayers,
//            @RequestParam(required = false) Integer maxPlayers,
//            @RequestParam(required = false) Integer minTime,
//            @RequestParam(required = false) Integer maxTime,
//            @RequestParam(required = false) Integer size,
//            @RequestParam(required = false) Integer page,
//            @RequestParam(required = false) String direction) {
//
//        if (minPlayers == null) minPlayers = boardGameRepository.getMinPlayersInUserCollection(userId);
//        if (maxPlayers == null) maxPlayers = boardGameRepository.getMaxPlayersInUserCollection(userId);
//        if (minTime == null) minTime = boardGameRepository.getMinTimeInUserCollection(userId);
//        if (maxTime == null) maxTime = boardGameRepository.getMaxTimeInUserCollection(userId);
//
//        return customRepository.getBoardGamesByFilterParams(userId,
//                minPlayers, maxPlayers, minTime, maxTime, page, size);
//
//    }


}
