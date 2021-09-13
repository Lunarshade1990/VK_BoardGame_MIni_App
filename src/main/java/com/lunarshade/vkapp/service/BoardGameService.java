package com.lunarshade.vkapp.service;

import com.lunarshade.vkapp.dto.request.BoardGameFilter;
import com.lunarshade.vkapp.entity.BoardGame;
import com.lunarshade.vkapp.entity.CollectionType;
import com.lunarshade.vkapp.entity.QBoardGame;
import com.lunarshade.vkapp.repository.BoardGameRepository;
import com.lunarshade.vkapp.repository.util.QPredicates;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardGameService {

    private final BoardGameRepository boardGameRepository;


    public Page<BoardGame> getBoardGamesByOwnerAndCollectionTypeWithFilter(long userId,
                                                                           CollectionType type,
                                                                           BoardGameFilter filter,
                                                                           Pageable pageable) {
        QBoardGame boardGame = QBoardGame.boardGame;
        QPredicates  qPredicates = QPredicates.builder()
                .add(filter.getMinTime(), boardGame.minTime::goe)
                .add(filter.getMaxTime(), boardGame.maxTime::loe)
                .add(userId, boardGame.boardGameCollections.any().collection.appUser.id::eq)
                .add(type, boardGame.boardGameCollections.any().collection.collectionType::eq);

        try {
            switch (filter.getModeName()) {
                case SOLO, DUEL_PLUS -> qPredicates
                        .add(filter.getMinPlayer(), boardGame.minPlayerNumber::goe)
                        .add(filter.getMaxPlayer(), boardGame.maxPlayerNumber::loe);
                case DUEL -> QPredicates.builder()
                        .add(filter.getMinPlayer(), boardGame.minPlayerNumber::eq)
                        .add(filter.getMaxPlayer(), boardGame.maxPlayerNumber::eq);
                case COMPANY -> QPredicates.builder()
                        .add(filter.getMinPlayer(), boardGame.minPlayerNumber::loe)
                        .add(filter.getMaxPlayer(), boardGame.maxPlayerNumber::goe);
            }
        } catch (BoardGameFilter.IncompatibleFilterParameters ex) {
            return Page.empty();
        }

        return boardGameRepository.findAll(qPredicates.buildAnd(), pageable);

    }

}
