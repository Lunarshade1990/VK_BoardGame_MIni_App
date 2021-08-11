package com.lunarshade.vkapp.service.teseraService;

import com.lunarshade.vkapp.service.TeseraService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GetUserGameCollectionTest extends Assertions {

    String username = "ShadeFromTwoMoons";
    @Autowired
    TeseraService teseraService;

    @Test
    void getUserGameListSuccessResponse () throws InterruptedException {
        assertEquals(244, teseraService.getUserGameCollection(username).size());
    }


}
