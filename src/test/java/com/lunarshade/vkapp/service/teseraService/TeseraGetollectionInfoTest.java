package com.lunarshade.vkapp.service.teseraService;


import com.lunarshade.vkapp.service.TeseraService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TeseraGetollectionInfoTest extends Assertions {
    String username = "ShadeFromTwoMoons";
    @Autowired
    TeseraService teseraService;

    @Test
    void getUserCollectionListSuccessResponse () {
        int size = 4;
        assertEquals(size, teseraService.getUserCollectionsInfo(username).getCollections().size());
    }

}
