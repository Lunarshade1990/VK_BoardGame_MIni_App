package com.lunarshade.vkapp.service.teseraService;

import com.lunarshade.vkapp.dto.tesera.TeseraGame;
import com.lunarshade.vkapp.service.TeseraService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;

public class GetUserGameCollectionWithFullInfoTest extends Assertions {

    static TeseraService teseraService = new TeseraService();
    static String username = "ShadeFromTwoMoons";
    static List<TeseraGame> teseraGames;

    @BeforeAll
    static void initMethod() throws InterruptedException {
        teseraGames = teseraService.getUserGameCollectionWithFullInfo(username);
        teseraGames.forEach(System.out::println);
    }

    @Test
    void listSize() {
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
