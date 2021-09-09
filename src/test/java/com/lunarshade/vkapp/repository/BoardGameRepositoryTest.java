package com.lunarshade.vkapp.repository;

import com.lunarshade.vkapp.dao.userdao.BoardGameInfo;
import com.lunarshade.vkapp.entity.CollectionType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.transaction.Transactional;

@SpringBootTest
public class BoardGameRepositoryTest extends Assertions {
    @Autowired
    BoardGameRepository boardGameRepository;

    @Test
    @Transactional
    void getBoardGameByCollectionTypeAndUserIdTest() {
        Page<BoardGameInfo> gameInfos = boardGameRepository
                .getBoardGameByCollectionTypeAndUserId(1L, CollectionType.OWN, PageRequest.of(1, 10, Sort.by("bgc.added").descending()));
        gameInfos.getContent().stream().map(BoardGameInfo::getId).forEach(System.out::println);

        assertEquals(247, gameInfos.getTotalElements());
    }
}
