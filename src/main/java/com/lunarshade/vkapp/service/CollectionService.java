package com.lunarshade.vkapp.service;

import com.lunarshade.vkapp.dao.CollectionDto;
import com.lunarshade.vkapp.entity.*;
import com.lunarshade.vkapp.repository.CollectionRepository;
import com.lunarshade.vkapp.repository.util.QPredicates;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;


@RequiredArgsConstructor
@Service
public class CollectionService {

    private final CollectionRepository collectionRepository;
    private final EntityManager entityManager;


    public CollectionDto getCollectionInfoByUserIdAndCollectionType(Long userId, CollectionType type) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        QCollection collection = QCollection.collection;
        QBoardGame boardGame = QBoardGame.boardGame;
        QAppUser appUser = QAppUser.appUser;
        QBoardGameCollection boardGameCollection = QBoardGameCollection.boardGameCollection;

        QPredicates qPredicates = QPredicates.builder();
        qPredicates
                .add(userId, appUser.id::eq)
                .add(type, collection.collectionType::eq);

        return queryFactory.select(
                Projections.constructor(CollectionDto.class,
                        boardGame.minTime.min().as("minTime"),
                        boardGame.maxTime.max().as("maxTime"),
                        boardGame.minPlayerNumber.min().as("minPlayerNumber"),
                        boardGame.maxPlayerNumber.max().as("maxPlayerNumber"),
                        boardGameCollection.count().as("totalElements")))
                .from(appUser)
                .innerJoin(appUser.collections, collection)
                .innerJoin(collection.boardGameCollection, boardGameCollection)
                .innerJoin(boardGameCollection.boardGame, boardGame)
                .where(qPredicates.buildAnd())
                .fetchOne();
    }



}
