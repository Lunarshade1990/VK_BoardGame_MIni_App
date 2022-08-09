package com.lunarshade.vkapp.service;

import com.lunarshade.vkapp.dao.tesera.TeseraGame;
import com.lunarshade.vkapp.dao.tesera.TeseraUserCollectionGame;
import com.lunarshade.vkapp.dao.tesera.TeseraUserCollectionInfo;
import com.lunarshade.vkapp.dao.tesera.TeseraUserCollectionInfo.CollectionType;
import com.lunarshade.vkapp.entity.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeseraService {

    private final UserService userService;

    RestTemplate restTemplate = new RestTemplate();
    private final String BASE_URL= "https://api.tesera.ru";
    private final int limit = 15;
    // A service that calls out over HTTP

    @Transactional
    public DeferredResult<ResponseEntity<Void>> importCollection(String nickname, AppUser appUser) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        DeferredResult<ResponseEntity<Void>> output = new DeferredResult<>();
        executorService.execute(() -> {
            try {
                List<TeseraGame> teseraGameList = getUserGameCollectionWithFullInfo(nickname);
                List<TeseraGame.Game> teseraGames = teseraGameList.stream().map(TeseraGame::getGame).toList();
                userService.saveGameCollection(teseraGames, appUser, com.lunarshade.vkapp.entity.CollectionType.OWN);
                output.setResult(new ResponseEntity<>(HttpStatus.OK));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        return output;
    }


    public List<TeseraGame> getUserGameCollectionWithFullInfo (String nickname) throws InterruptedException {
        List<TeseraUserCollectionGame> games = getUserGameCollection(nickname);
        return games.parallelStream().map(game -> {
            TeseraGame teseraGame = getFullGameInfo(game.getGame().getTeseraId());
            teseraGame.getGame().setAddedIntoCollection(game.getCreationDateUtc());
            return teseraGame;
        }).collect(Collectors.toList());
    }


    public List<TeseraUserCollectionGame> getUserGameCollection (String nickname) throws InterruptedException {
        int gameCount = getUserCollectionsInfo(nickname)
                .generateCollectionInfo()
                .get(CollectionType.Own);
        int pageCount = getPageCount(gameCount);
        return getTeseraUserCollectionGames(nickname, pageCount);
    }

    private List<TeseraUserCollectionGame> getTeseraUserCollectionGames(String nickname, int pageCount) throws InterruptedException {
        List<TeseraUserCollectionGame> gameList = new ArrayList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(6);
        CountDownLatch latch = new CountDownLatch(pageCount);
        IntStream.range(0, pageCount).forEach(i -> {
            executorService.submit(() -> {
                gameList.addAll(getGamesForOffset(nickname, i));
                latch.countDown();
            });
        });
        latch.await();
        return gameList;
    }

    public TeseraGame getFullGameInfo(long id) {
        String url = String.format("%s/games/%d", BASE_URL, id);
        return restTemplate.getForObject(url, TeseraGame.class);
    }

    public TeseraUserCollectionInfo getUserCollectionsInfo(String nickname) {
        String url = String.format("%s/collections/user/%s", BASE_URL, nickname);
        return restTemplate.getForObject(url, TeseraUserCollectionInfo.class);
    }

    private List<TeseraUserCollectionGame> getGamesForOffset(String nickname, int pageId) {
        String url = String.format("%s/collections/base/own/%s?limit=15&offset=%d", BASE_URL, nickname, pageId);
        ResponseEntity<List<TeseraUserCollectionGame>> responseEntity
                = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
        return responseEntity.getBody();
    }

    private int getPageCount(int gameCount) {
        return (int) Math.ceil((double) gameCount / limit);
    }


}
