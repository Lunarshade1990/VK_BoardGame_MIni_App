package com.lunarshade.vkapp.controller;

import com.lunarshade.vkapp.entity.AppUser;
import com.lunarshade.vkapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "https://localhost:10888", maxAge = 3600)
public class UserController {

    @Autowired
    UserService userService;


    @GetMapping("/{id}")
    public AppUser getUserByVkId(@PathVariable String id) {
        return userService.getByProviderId(id);
    }

}
