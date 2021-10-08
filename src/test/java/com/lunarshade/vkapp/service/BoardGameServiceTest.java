package com.lunarshade.vkapp.service;

import com.lunarshade.vkapp.dao.CollectionDto;
import com.lunarshade.vkapp.dao.request.BoardGameFilter;
import com.lunarshade.vkapp.entity.CollectionType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@SpringBootTest
public class BoardGameServiceTest extends Assertions {
    @Autowired
    BoardGameService service;
    @Autowired
    CollectionService collectionService;

    static BoardGameFilter filter = new BoardGameFilter();

    @BeforeAll
    static void init() {
//        filter.setPlayers(new int[]{0, 999});
//        filter.setTime(new int[]{0, 999});
//        filter.setMode(new int[]{0, 999});
        filter.setSearch("small");
    }

    @Test
    @Transactional
    void getCollectionInfo() {
        CollectionDto collectionDto = collectionService.getCollectionInfoByUserIdAndCollectionType(1, CollectionType.OWN);
        assertEquals(8, collectionDto.getMaxPlayerNumber());
    }


}
