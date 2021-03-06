package com.lunarshade.vkapp.service;

import com.lunarshade.vkapp.dao.tesera.TeseraGame;
import com.lunarshade.vkapp.dao.vkUser.VkRequestUser;
import com.lunarshade.vkapp.entity.*;
import com.lunarshade.vkapp.repository.BoardGameRepository;
import com.lunarshade.vkapp.repository.CollectionRepository;
import com.lunarshade.vkapp.repository.UserRepository;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BoardGameRepository boardGameRepository;
    private final CollectionRepository collectionRepository;

    public UserService(UserRepository repository, BoardGameRepository boardGameRepository, CollectionRepository collectionRepository) {
        this.userRepository = repository;
        this.boardGameRepository = boardGameRepository;
        this.collectionRepository = collectionRepository;
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

    public void saveGameCollection (List<TeseraGame> games, AppUser appUser, CollectionType type) {
        Collection collection = appUser.getCollectionByType(type);
        collection.getBoardGameCollection().clear();
        Set<BoardGameCollection> boardGameCollections = collection.getBoardGameCollection();

        List<BoardGame> boardGames = games.parallelStream()
                .map(teseraGame -> {
                    TeseraGame.Game game = teseraGame.getGame();
                    BoardGame boardGame = boardGameRepository.findByTeseraId(game.getTeseraId());
                    if (boardGame == null) {
                        boardGame = new BoardGame();
                    }
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
                    BoardGameCollection boardGameCollection = new BoardGameCollection();
                    boardGameCollection.setBoardGame(boardGame);
                    boardGameCollection.setCollection(collection);
                    boardGameCollection.setAdded(game.getAddedIntoCollection());
                    boardGameCollections.add(boardGameCollection);
                    boardGame.getBoardGameCollections().add(boardGameCollection);
                    return boardGame;
                }).collect(Collectors.toList());

        collectionRepository.save(collection);
        boardGameRepository.saveAll(boardGames);
        userRepository.save(appUser);
    }


    public Set<Event> getUserPlays (AppUser user) {
        return user.getEvents();
    }

}
