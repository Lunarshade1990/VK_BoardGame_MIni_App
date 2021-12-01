package com.lunarshade.vkapp.service;

import com.lunarshade.vkapp.dao.tesera.TeseraGame;
import com.lunarshade.vkapp.dao.vkUser.VkRequestUser;
import com.lunarshade.vkapp.entity.*;
import com.lunarshade.vkapp.repository.BoardGameCollectionRepository;
import com.lunarshade.vkapp.repository.BoardGameRepository;
import com.lunarshade.vkapp.repository.CollectionRepository;
import com.lunarshade.vkapp.repository.UserRepository;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BoardGameRepository boardGameRepository;
    private final CollectionRepository collectionRepository;
    private final BoardGameCollectionRepository boardGameCollectionRepository;

    public UserService(UserRepository repository, BoardGameRepository boardGameRepository, CollectionRepository collectionRepository, BoardGameCollectionRepository boardGameCollectionRepository) {
        this.userRepository = repository;
        this.boardGameRepository = boardGameRepository;
        this.collectionRepository = collectionRepository;
        this.boardGameCollectionRepository = boardGameCollectionRepository;
    }

    public AppUser find(long id) {
        return userRepository.findById(id).orElse(null);
    }

    public AppUser saveUser(AppUser user) {
        return userRepository.save(user);
    }

    public AppUser getByProviderId(String id) {
        return userRepository.getByProviderId(id);
    }

    public AppUser saveUserFromVkMiniApp(VkRequestUser user) {
        AppUser appUser = new AppUser();
        City city = new City();
        city.setVkId(user.city().id());
        city.setName(user.city().title());
        appUser.setAdapter("Vkontakte");
        appUser.setProviderId(String.valueOf(user.id()));
        appUser.setFirstName(user.first_name());
        appUser.setSecondName(user.last_name());
        appUser.setRoles(Collections.singletonList(Roles.USER));
        appUser.setCity(city);
        appUser.setAvatarUrl(user.photo_100());
        return  userRepository.save(appUser);
    }

    public void saveGameCollection (List<TeseraGame.Game> games, AppUser appUser, CollectionType type) {
        deleteGamesFromCollection(appUser, games, type);
        updateGamesAlreadyWasInCollection(appUser, games, type);
        addNewGamesInUserCollection(appUser, games, type);
    }


    public Set<Event> getUserPlays (AppUser user) {
        return user.getEvents();
    }

    private void deleteGamesFromCollection (AppUser user, List<TeseraGame.Game> teseraGames, CollectionType type) {
        List<BoardGameCollection> gameCollections = filterUserCollectionByCondition(boardGameCollection ->
                !containsIdInTeseraGameList(teseraGames, boardGameCollection.getBoardGame().getTeseraId())
                        && boardGameCollection.getDeleted() == null, user, type);

        gameCollections.forEach(boardGameCollection -> boardGameCollection.setDeleted(new Date()));
        boardGameCollectionRepository.saveAll(gameCollections);
    }

    private void updateGamesAlreadyWasInCollection (AppUser user, List<TeseraGame.Game> teseraGames, CollectionType type) {
        List<BoardGameCollection> gameCollections = filterUserCollectionByCondition(boardGameCollection ->
                containsIdInTeseraGameList(teseraGames, boardGameCollection.getBoardGame().getTeseraId())
                        && boardGameCollection.getDeleted() != null, user, type);
        gameCollections.forEach(boardGameCollection -> {
            boardGameCollection.setAdded(new Date());
            boardGameCollection.setDeleted(null);
        });
        boardGameCollectionRepository.saveAll(gameCollections);
    }

    private void addNewGamesInUserCollection (AppUser user, List<TeseraGame.Game> teseraGames, CollectionType type) {
        List<TeseraGame.Game> gamesNotInUserCollection = getGamesNotInUserCollection(user, teseraGames, type);
        List<Long> teseraIds = gamesNotInUserCollection.stream().map(TeseraGame.Game::getTeseraId).toList();
        List<BoardGame> gamesInBd = getGamesInBd(teseraIds);
        List<Long> gamesInBdId = gamesInBd.stream().map(BoardGame::getTeseraId).toList();
        Predicate<TeseraGame.Game> predicate = game -> !gamesInBdId.contains(game.getTeseraId());
        List<TeseraGame.Game> gamesNotInBd = filterBoardGamesByCondition(predicate, gamesNotInUserCollection);
        Collection collection = getUserCollectionByType(user, type);
        userRepository.save(user);
        bindGamesWithUserCollection(collection, gamesInBd, teseraGames);
        List<BoardGame> newBoardGames= createNewGameFromTeseraGameList(gamesNotInBd);
        bindGamesWithUserCollection(collection, newBoardGames, teseraGames);
    }

    private List<BoardGame> createNewGameFromTeseraGameList(List<TeseraGame.Game> teseraGameList) {
        return teseraGameList.stream().map(game -> {
            BoardGame boardGame = new BoardGame();
            boardGame.setName(game.getTitle());
            boardGame.setName2(game.getTitle2());
            boardGame.setPicture(game.getPhotoUrl());
            boardGame.setDescription(game.getDescription() == null ? "" : Jsoup.parse(game.getDescription()).text());
            boardGame.setTeseraId(game.getTeseraId());
            boardGame.setBggId(game.getBggId());
            boardGame.setMinPlayerNumber(game.getPlayersMin());
            boardGame.setMaxPlayerNumber(game.getPlayersMax());
            boardGame.setMinTime(game.getPlaytimeMin());
            boardGame.setMaxTime(game.getPlaytimeMax());
            return boardGameRepository.save(boardGame);
        }).toList();
    }

    void bindGamesWithUserCollection(Collection collection, List<BoardGame> boardGames, List<TeseraGame.Game> teseraGames) {

        boardGames.forEach(boardGame -> {
            Date addedIntoCollection = teseraGames.stream()
                    .filter(teseraGame -> teseraGame.getTeseraId() == boardGame.getTeseraId())
                    .findFirst().get()
                    .getAddedIntoCollection();
            BoardGameCollection boardGameCollection = new BoardGameCollection();
            boardGameCollection.setCollection(collection);
            collection.getBoardGameCollection().add(boardGameCollection);
            boardGameCollection.setBoardGame(boardGame);
            boardGame.getBoardGameCollections().add(boardGameCollection);
            boardGameCollection.setAdded(addedIntoCollection);
        });
        boardGameRepository.saveAll(boardGames);
    }

    public List<BoardGameCollection> filterUserCollectionByCondition(Predicate<BoardGameCollection> predicate, AppUser user, CollectionType type) {
        return getUserCollectionByType(user, type)
                .getBoardGameCollection().stream()
                .filter(predicate)
                .toList();
    }

    private List<TeseraGame.Game> getGamesNotInUserCollection(AppUser user, List<TeseraGame.Game> teseraGames, CollectionType type) {
        Collection collection = getUserCollectionByType(user, type);
        Set<BoardGameCollection> boardGameCollection = collection.getBoardGameCollection();
        return teseraGames.stream()
                .filter(game -> !containsInUserCollection(boardGameCollection, game.getTeseraId()))
                .toList();
    }

    private List<BoardGame> getGamesInBd(List<Long> teseraIds) {
        return boardGameRepository.findAllByTeseraIdIn(teseraIds);
    }

    private List<TeseraGame.Game> filterBoardGamesByCondition (Predicate<TeseraGame.Game> predicate, List<TeseraGame.Game> games) {
        return games.stream().filter(predicate).toList();
    }



    private boolean containsIdInTeseraGameList(List<TeseraGame.Game> teseraGames, Long id) {
        return getTeseraIdsFromGameList(teseraGames).contains(id);
    }

    private boolean containsInUserCollection(Set<BoardGameCollection> collections, Long teseraId) {
        return collections.stream().map(BoardGameCollection::getBoardGame)
                .anyMatch(boardGame -> boardGame.getTeseraId() == teseraId);
    }

    private List<Long> getTeseraIdsFromGameList(List<TeseraGame.Game> teseraGames) {
        return teseraGames.stream()
                .map(TeseraGame.Game::getTeseraId)
                .toList();
    }

    public Collection getUserCollectionByType(AppUser user, CollectionType type) {
        Collection collection = collectionRepository.findByAppUserAndCollectionType(user, type);
        if (collection == null) {
            collection = new Collection(type);
            collection.setAppUser(user);
            collection.setCollectionType(type);
            user.getCollections().add(collection);
            saveUser(user);
            return collectionRepository.findByAppUserAndCollectionType(user, type);
        }
        return collection;
    }

}
