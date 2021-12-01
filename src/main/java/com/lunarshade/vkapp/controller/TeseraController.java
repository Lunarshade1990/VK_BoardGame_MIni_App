package com.lunarshade.vkapp.controller;


import com.lunarshade.vkapp.dao.tesera.TeseraGame;
import com.lunarshade.vkapp.entity.AppUser;
import com.lunarshade.vkapp.entity.CollectionType;
import com.lunarshade.vkapp.service.TeseraService;
import com.lunarshade.vkapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        AppUser appUser = userService.getByProviderId(authentication.getName());

        appUser.setTeseraProfile(nickname);
        userService.saveUser(appUser);

        DeferredResult<ResponseEntity<Void>> output = new DeferredResult<>();
        executorService.execute(() -> {
            try {
                List<TeseraGame> teseraGameList = teseraService.getUserGameCollectionWithFullInfo(nickname);
                List<TeseraGame.Game> teseraGames = teseraGameList.stream().map(TeseraGame::getGame).toList();
                userService.saveGameCollection(teseraGames, appUser, CollectionType.OWN);
                output.setResult(new ResponseEntity<>(HttpStatus.OK));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        return output;
    }

}
