package com.lunarshade.vkapp.service;


import com.lunarshade.vkapp.dto.request.BoardGameFilter;
import com.lunarshade.vkapp.entity.BoardGame;
import com.lunarshade.vkapp.entity.CollectionType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@SpringBootTest
public class BoardGameServiceTest extends Assertions {
    @Autowired
    BoardGameService service;

    static BoardGameFilter filter = new BoardGameFilter();

    @BeforeAll
    static void init() {
        filter.setPlayers(new int[]{0, 999});
        filter.setTime(new int[]{0, 999});
        filter.setMode(new int[]{1});
        filter.setModeName(BoardGameFilter.ModeName.SOLO);
    }

    @Test
    void getBoardGamesByOwnerAndCollectionTypeWithFilterTest() {

        Page<BoardGame> page =
                service.getBoardGamesByOwnerAndCollectionTypeWithFilter(
                        1,
                        CollectionType.OWN,
                        filter,
                        PageRequest.of(1, 10));

        assertTrue(page.getContent().size() > 0);
    }


}
