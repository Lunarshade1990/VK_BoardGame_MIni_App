package com.lunarshade.vkapp.service;

import com.lunarshade.vkapp.dao.BoardGameDao;
import com.lunarshade.vkapp.dao.request.BoardGameFilter;
import com.lunarshade.vkapp.entity.*;
import com.lunarshade.vkapp.repository.BoardGameRepository;
import com.lunarshade.vkapp.repository.response.PageResponse;
import com.lunarshade.vkapp.repository.util.QPredicates;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardGameService {

    private final BoardGameRepository boardGameRepository;
    private final EntityManager em;


    public PageResponse<BoardGameDao> getBoardGamesByOwnerAndCollectionTypeWithFilter(Long userId,
                                                                                      CollectionType type,
                                                                                      BoardGameFilter filter,
                                                                                      Pageable pageable) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QBoardGame boardGame = QBoardGame.boardGame;
        QBoardGameCollection boardGameCollection = QBoardGameCollection.boardGameCollection;
        QCollection collection = QCollection.collection;
        QAppUser appUser = QAppUser.appUser;

        QPredicates  qPredicates = QPredicates.builder();

        if (type != CollectionType.ALL) {
            qPredicates
                    .add(boardGame.boardGameCollections.any().deleted.isNull())
                    .add(userId, boardGame.boardGameCollections.any().collection.appUser.id::eq)
                    .add(type, boardGame.boardGameCollections.any().collection.collectionType::eq);
        }

        if (filter.getModeName() != null) {
            try {
                switch (filter.getModeName()) {
                    case SOLO, DUEL_PLUS -> qPredicates
                            .add(filter.getMinPlayer(), boardGame.minPlayerNumber::eq)
                            .add(filter.getMaxPlayer(), boardGame.maxPlayerNumber::goe);
                    case DUEL -> qPredicates
                            .add(filter.getMinPlayer(), boardGame.minPlayerNumber::eq)
                            .add(filter.getMaxPlayer(), boardGame.maxPlayerNumber::eq);
                    case COMPANY -> qPredicates
                            .add(filter.getMinPlayer(), boardGame.minPlayerNumber::loe)
                            .add(filter.getMaxPlayer(), boardGame.maxPlayerNumber::goe);
                }
            } catch (BoardGameFilter.IncompatibleFilterParameters ex) {
                return PageResponse.emptyList();
            }
        } else {
            qPredicates
                    .add(filter.getMinTime(), boardGame.minTime::goe)
                    .add(filter.getMaxTime(), boardGame.maxTime::loe);
        }

        String searchParam = filter.getSearch() == null ? null : filter.getSearch();
        
        Predicate search = QPredicates.builder()
                .add(searchParam, boardGame.name::containsIgnoreCase)
                .add(searchParam, boardGame.name2::containsIgnoreCase)
                .buildOr();

        Predicate finalPredicate = ExpressionUtils.and(search, qPredicates.buildAnd());

        long totalElements =  queryFactory
                .selectFrom(boardGame)
                .innerJoin(boardGame.boardGameCollections, boardGameCollection)
                .innerJoin(boardGameCollection.collection, collection)
                .innerJoin(collection.appUser, appUser)
                .where(finalPredicate)
                .stream().count();

        List<BoardGame> boardGames = queryFactory
                .selectFrom(boardGame)
                .innerJoin(boardGame.boardGameCollections, boardGameCollection)
                .innerJoin(boardGameCollection.collection, collection)
                .innerJoin(collection.appUser, appUser)
                .where(finalPredicate)
                .offset(pageable.getOffset())
                .orderBy(boardGameCollection.added.desc())
                .limit(pageable.getPageSize())
                .fetch();

        List<BoardGameDao> boardGameDaoList = boardGames.stream().map(BoardGameDao::new).toList();

        return new PageResponse<>(boardGameDaoList, totalElements, pageable.getPageSize());
    }



}
