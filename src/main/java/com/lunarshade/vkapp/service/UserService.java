package com.lunarshade.vkapp.service;

import com.lunarshade.vkapp.dao.tesera.TeseraGame;
import com.lunarshade.vkapp.dao.vkUser.VkRequestUser;
import com.lunarshade.vkapp.entity.*;
import com.lunarshade.vkapp.repository.BoardGameRepository;
import com.lunarshade.vkapp.repository.UserRepository;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BoardGameRepository boardGameRepository;

    public UserService(UserRepository repository, BoardGameRepository boardGameRepository) {
        this.userRepository = repository;
        this.boardGameRepository = boardGameRepository;
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
        return  userRepository.save(appUser);
    }

    public void saveGameCollection (List<TeseraGame> games, AppUser appUser) {
        var newBoardGames =  games.parallelStream()
                .map(teseraGame -> {
                    var boardGame = new BoardGame();
                    TeseraGame.Game game = teseraGame.getGame();
                    boardGame.setAddedToCollection(game.getAddedIntoCollection());
                    boardGame.setName(game.getTitle());
                    boardGame.setName2(game.getTitle2());
                    boardGame.setPicture(game.getPhotoUrl());
                    boardGame.setDescription(game.getDescription() == null ? "" : Jsoup.parse(game.getDescription()).text());
                    boardGame.setTeseraId(game.getTeseraId());
                    boardGame.setBggId(game.getBggId());
                    var boardGameInfo = new BoardGameInfo();
                    boardGameInfo.setMinPlayerNumber(game.getPlayersMin());
                    boardGameInfo.setMaxPlayerNumber(game.getPlayersMax());
                    boardGameInfo.setMinTime(game.getPlaytimeMin());
                    boardGameInfo.setMaxTime(game.getPlaytimeMax());
                    var appUsers = getGameAppUsers(boardGame);
                    boardGame.setBoardGameInfo(boardGameInfo);
                    appUsers.add(appUser);
                    return boardGame;
                }).collect(Collectors.toSet());

        var userBoardGames = getUserBoardGames(appUser);
        userBoardGames.addAll(newBoardGames);

        boardGameRepository.saveAll(userBoardGames);
        userRepository.save(appUser);
    }


    private Set<BoardGame> getUserBoardGames (AppUser appUser) {
        Set<BoardGame> boardGames = appUser.getBoardGames();
        if (boardGames == null) {
            boardGames = new HashSet<>();
            appUser.setBoardGames(boardGames);
        }
        return boardGames;
    }

    private Set<AppUser> getGameAppUsers (BoardGame boardGame) {
        Set<AppUser> appUsers = boardGame.getAppUsers();
        if (appUsers == null) {
            appUsers = new HashSet<>();
            boardGame.setAppUsers(appUsers);
        }
        return  appUsers;
    }


}
