package com.lunarshade.vkapp.controller;


import com.lunarshade.vkapp.entity.AppUser;
import com.lunarshade.vkapp.service.TeseraService;
import com.lunarshade.vkapp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
@RequestMapping("/tesera/")
@CrossOrigin
public class TeseraController {

    private final TeseraService teseraService;
    private final UserService userService;

    public TeseraController(TeseraService teseraService, UserService userService) {
        this.teseraService = teseraService;
        this.userService = userService;
    }

    @GetMapping("import")
    public DeferredResult<ResponseEntity<Void>> importGamesFromTesera(@RequestParam String nickname, Authentication authentication) {
        AppUser appUser = userService.getByProviderId(authentication.getName());
        return  teseraService.importCollection(nickname, appUser);
    }

}
