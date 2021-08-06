package com.lunarshade.vkapp.service;

import com.lunarshade.vkapp.dto.tesera.TeseraUserCollectionInfo;
import com.lunarshade.vkapp.dto.tesera.TeseraGame;
import com.lunarshade.vkapp.dto.tesera.TeseraUserCollectionGame;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class TeseraService {

    RestTemplate restTemplate = new RestTemplate();
    private final String BASE_URL= "https://api.tesera.ru";
    private final int limit = 15;

//    public List<TeseraUserCollectionGame> getUserGameCollection (String nickname) {
//        String url = String.format("%s/collections/base/own/%s?limit=1000&offset=1", BASE_URL, nickname);
//
//    }

    public TeseraGame getFullGameInfo(String id) {
        String url = String.format("%s/games/%s", BASE_URL, id);
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
