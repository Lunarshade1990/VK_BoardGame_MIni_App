package com.lunarshade.vkapp.service.teseraService;

import com.lunarshade.vkapp.dao.tesera.TeseraGame;
import com.lunarshade.vkapp.service.TeseraService;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;

import java.util.List;
import java.util.Objects;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@SpringBootTest
@AutoConfigureWireMock
public class GetUserGameCollectionWithFullInfoTest extends Assertions {

    @Autowired TeseraService teseraService;
    static String username = "ShadeFromTwoMoons";
    static List<TeseraGame> teseraGames;
    CloseableHttpClient client;

//    @BeforeAll
//    static void initMethod(@Autowired TeseraService teseraService) throws InterruptedException {
//    }

    @Test
    void listSize() throws InterruptedException {
        stubFor(get(urlEqualTo("https://api.tesera.ru/collections/user/ShadeFromTwoMoons"))
                .willReturn(aResponse().withHeader("Content-Type", "text/plain").withBody("Hello World!")));
        teseraGames = teseraService.getUserGameCollectionWithFullInfo(username);
        teseraGames.forEach(System.out::println);
        assertEquals(244, teseraGames.size());
    }

    @Test
    void CollectionDoesntContainNullObj() throws InterruptedException {
        assertTrue(teseraGames.stream().allMatch(Objects::nonNull));
    }

    @Test
    void CollectionDoesntContainEmpty() throws InterruptedException {
        assertTrue(teseraGames.stream().allMatch(teseraGame -> teseraGame.getGame().getTeseraId() != 0));
    }

}
