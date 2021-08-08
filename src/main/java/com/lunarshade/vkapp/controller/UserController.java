package com.lunarshade.vkapp.controller;

import com.lunarshade.vkapp.entity.AppUser;
import com.lunarshade.vkapp.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public AppUser getUserByVkId(@PathVariable String id) {
        return userService.getByProviderId(id);
    }

}
