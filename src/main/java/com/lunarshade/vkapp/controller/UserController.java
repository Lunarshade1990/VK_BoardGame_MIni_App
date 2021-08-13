package com.lunarshade.vkapp.controller;

import com.lunarshade.vkapp.dao.userdao.UserDao;
import com.lunarshade.vkapp.entity.AppUser;
import com.lunarshade.vkapp.entity.BoardGame;
import com.lunarshade.vkapp.repository.BoardGameRepository;
import com.lunarshade.vkapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

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

    @GetMapping("/{user}/collection")
    @ResponseBody
    public Set<BoardGame> getUserCollection(@PathVariable AppUser user) {
        return user.getBoardGames();
    }

}
