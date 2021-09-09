package com.lunarshade.vkapp.controller;

import com.lunarshade.vkapp.dao.userdao.BoardGameInfo;
import com.lunarshade.vkapp.dao.userdao.CollectionDto;
import com.lunarshade.vkapp.dao.userdao.UserDao;
import com.lunarshade.vkapp.entity.CollectionType;
import com.lunarshade.vkapp.repository.BoardGameRepository;
import com.lunarshade.vkapp.repository.repositoryUtil.CollectionPageResponse;
import com.lunarshade.vkapp.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    final UserService userService;
    final BoardGameRepository boardGameRepository;

    public UserController(UserService userService, BoardGameRepository boardGameRepository) {
        this.userService = userService;
        this.boardGameRepository = boardGameRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDao> getUserByVkId(@PathVariable String id) {
        System.out.println("Request: ");
        UserDao userDao = new UserDao(userService.getByProviderId(id));
        System.out.println(userDao);
        return new ResponseEntity<>(userDao, HttpStatus.OK);
    }



    @GetMapping("/{user}/collection/{type}")
    @ResponseBody
    public CollectionPageResponse getUserCollectionWithFilter(@PathVariable long user,
                                                              @PathVariable CollectionType type,
                                                              @RequestParam(required = false) Integer size,
                                                              @RequestParam(required = false) Integer page,
                                                              @RequestParam(required = false) Integer[] players,
                                                              @RequestParam(required = false) Integer[] time,
                                                              @RequestParam(required = false) Integer[] mode,
                                                              @RequestParam(required = false) String modeName) {

        size = size==null ? 10 : size;
        page = page==null ? 0 : page-1;

        CollectionDto collectionInfo = new CollectionDto(boardGameRepository, user, type);
        int minPlayers = players == null ? collectionInfo.getMinPlayers() : players[0];
        int maxPlayers = players == null ? collectionInfo.getMaxPlayers() : players[1];
        int minTime = time == null ? collectionInfo.getMinTime() : time[0];
        int maxTime = time == null ? collectionInfo.getMaxTime() : time[1];
        Page<BoardGameInfo> collectionPage;

        if (modeName != null) {
            switch (modeName) {
                case "solo":
                    if (minPlayers > 1) return new CollectionPageResponse(Page.empty(), collectionInfo);
                    else {
                        collectionPage = boardGameRepository
                                .getBoardGameByCollectionTypeAndUserIdForSoloOrTwo(
                                        user,
                                        type,
                                        1,
                                        minTime,
                                        maxTime,
                                        PageRequest.of(page, size, Sort.by("bgc.added").descending())
                                );
                        return new CollectionPageResponse(collectionPage, collectionInfo);
                    }
                case "duel+":
                    if (minPlayers > 2) return new CollectionPageResponse(Page.empty(), collectionInfo);
                    else {
                        collectionPage = boardGameRepository
                                .getBoardGameByCollectionTypeAndUserIdForSoloOrTwo(
                                        user,
                                        type,
                                        2,
                                        minTime,
                                        maxTime,
                                        PageRequest.of(page, size, Sort.by("bgc.added").descending())
                                );
                        return new CollectionPageResponse(collectionPage, collectionInfo);
                    }
                case "duel":
                    if (minPlayers > 2) return new CollectionPageResponse(Page.empty(), collectionInfo);
                    else {
                        collectionPage = boardGameRepository
                                .getBoardGameByCollectionTypeAndUserIdForDuel(
                                        user,
                                        type,
                                        minTime,
                                        maxTime,
                                        PageRequest.of(page, size, Sort.by("bgc.added").descending())
                                );
                        return new CollectionPageResponse(collectionPage, collectionInfo);
                    }
                case "company":
                    if (minPlayers > mode[0] || maxPlayers < mode[1]) return new CollectionPageResponse(Page.empty(), collectionInfo);
                    else {
                        collectionPage = boardGameRepository
                                .getBoardGameByCollectionTypeAndUserIdForCompany(
                                        user,
                                        type,
                                        mode[0],
                                        mode[1],
                                        minTime,
                                        maxTime,
                                        PageRequest.of(page, size, Sort.by("bgc.added").descending())
                                );
                        return new CollectionPageResponse(collectionPage, collectionInfo);
                    }
            }
        }


        collectionPage = boardGameRepository
                .getBoardGameByCollectionTypeAndUserIdWithFilter(
                        user,
                        type,
                        minPlayers,
                        maxPlayers,
                        minTime,
                        maxTime,
                        PageRequest.of(page, size, Sort.by("bgc.added").descending())
                );

        return new CollectionPageResponse(collectionPage, collectionInfo);
    }
}
