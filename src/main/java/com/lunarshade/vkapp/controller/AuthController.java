package com.lunarshade.vkapp.controller;

import com.lunarshade.vkapp.dao.vkUser.VkRequestUser;
import com.lunarshade.vkapp.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class AuthController {
    final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/auth")
    @ResponseBody
    public VkRequestUser saveUserInfo(@RequestBody VkRequestUser vkUSer) {
        return vkUSer;
    }
}
