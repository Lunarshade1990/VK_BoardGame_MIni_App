//package com.lunarshade.vkapp;
//
//import com.lunarshade.vkapp.dao.userdao.BoardGameDao;
//import com.lunarshade.vkapp.dao.userdao.UserDao;
//import com.lunarshade.vkapp.repository.customRepository.BoardGameCustomRepository;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.List;
//
//
//@SpringBootTest
//public class BoardGameCustomRepositoryTest extends Assertions {
//
//    @Autowired
//    BoardGameCustomRepository repository;
//
//    @Test
//    void customQueryTest() {
//        List<UserDao> userDaos = repository.getBoardGameOwners(306);
//        userDaos.forEach(System.out::println);
//    }
//
//    @Test
//    void getBoardGames() {
//        List<BoardGameDao> boardGameDaoList = repository.getBoardGamesByFilterParams(1L, 0, 8, 0, 999, 1, 10);
//        boardGameDaoList.forEach(System.out::println);
//    }
//}
