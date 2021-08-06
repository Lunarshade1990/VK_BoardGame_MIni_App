package com.lunarshade.vkapp.service;

import com.lunarshade.vkapp.dto.tesera.TeseraGame;
import com.lunarshade.vkapp.dto.tesera.TeseraUserCollectionGame;
import com.lunarshade.vkapp.dto.tesera.TeseraUserCollectionInfo;
import com.lunarshade.vkapp.dto.tesera.TeseraUserCollectionInfo.CollectionType;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class TeseraService {

    RestTemplate restTemplate = new RestTemplate();
    private final String BASE_URL= "https://api.tesera.ru";
    private final int limit = 15;

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
