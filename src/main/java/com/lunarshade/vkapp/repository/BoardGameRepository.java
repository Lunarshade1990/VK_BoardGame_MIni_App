package com.lunarshade.vkapp.repository;

import com.lunarshade.vkapp.dao.userdao.BoardGameInfo;
import com.lunarshade.vkapp.entity.BoardGame;
import com.lunarshade.vkapp.entity.CollectionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface BoardGameRepository extends PagingAndSortingRepository<BoardGame, Long>, QuerydslPredicateExecutor<BoardGame> {

    @Query("select game from BoardGame game " +
            "inner join game.boardGameCollections bgc " +
            "inner join bgc.collection c " +
            "inner join c.appUser u " +
            "where u.id = ?1 and c.collectionType = ?2")
    Page<BoardGameInfo> getBoardGameByCollectionTypeAndUserId(long userId, CollectionType type, Pageable pageable);

    @Query("select game from BoardGame game " +
            "inner join game.boardGameCollections bgc " +
            "inner join bgc.collection c " +
            "inner join c.appUser u " +
            "where u.id = :userId and c.collectionType = :type " +
            "and game.minPlayerNumber >= :minPlayers " +
            "and game.maxPlayerNumber <= :maxPlayers " +
            "and game.minTime >= :minTime and game.maxTime <= :maxTime")
    Page<BoardGameInfo> getBoardGameByCollectionTypeAndUserIdWithFilter(@Param("userId") long userId,
                                                                        @Param("type") CollectionType type,
                                                                        @Param("minPlayers") Integer minPlayerNumber,
                                                                        @Param("maxPlayers") Integer maxPlayerNumber,
                                                                        @Param("minTime") Integer minTime,
                                                                        @Param("maxTime") Integer maxTime,
                                                                        Pageable pageable);




    @Query("select max(game.maxPlayerNumber) from BoardGame game " +
            "inner join game.boardGameCollections bgc " +
            "inner join bgc.collection c " +
            "inner join c.appUser u " +
            "where u.id = ?1 and c.collectionType = ?2")
    int getMaxPlayersInUserCollection(long userId, CollectionType type);

    @Query("select min(game.minPlayerNumber) from BoardGame game " +
            "inner join game.boardGameCollections bgc " +
            "inner join bgc.collection c " +
            "inner join c.appUser u " +
            "where u.id = ?1 and c.collectionType = ?2")
    int getMinPlayersInUserCollection(long userId, CollectionType type);

    @Query("select min(game.minTime) from BoardGame game " +
            "inner join game.boardGameCollections bgc " +
            "inner join bgc.collection c " +
            "inner join c.appUser u " +
            "where u.id = ?1 and c.collectionType = ?2")
    int getMinTimeInUserCollection(long userId, CollectionType type);

    @Query("select max(game.maxTime) from BoardGame game " +
            "inner join game.boardGameCollections bgc " +
            "inner join bgc.collection c " +
            "inner join c.appUser u " +
            "where u.id = ?1 and c.collectionType = ?2")
    int getMaxTimeInUserCollection(long userId, CollectionType type);

}