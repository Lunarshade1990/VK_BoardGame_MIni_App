package com.lunarshade.vkapp.controller;

import com.lunarshade.vkapp.dao.BoardGameDao;
import com.lunarshade.vkapp.dao.CollectionDto;
import com.lunarshade.vkapp.dao.request.BoardGameFilter;
import com.lunarshade.vkapp.dao.userdao.UserDao;
import com.lunarshade.vkapp.entity.CollectionType;
import com.lunarshade.vkapp.repository.BoardGameRepository;
import com.lunarshade.vkapp.repository.response.PageResponse;
import com.lunarshade.vkapp.repository.response.collection.CollectionPageResponse;
import com.lunarshade.vkapp.service.BoardGameService;
import com.lunarshade.vkapp.service.CollectionService;
import com.lunarshade.vkapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final BoardGameRepository boardGameRepository;
    private final BoardGameService boardGameService;
    private final CollectionService collectionService;

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
                                                              BoardGameFilter filter) {

        size = size==null ? 10 : size;
        page = page==null ? 0 : page-1;

        CollectionDto collectionInfo = collectionService.getCollectionInfoByUserIdAndCollectionType(user, type);
        PageResponse<BoardGameDao> collectionPage = boardGameService
                .getBoardGamesByOwnerAndCollectionTypeWithFilter(user, type, filter, PageRequest.of(page, size));
        return new CollectionPageResponse(collectionPage, collectionInfo);
    }
}
