package com.lunarshade.vkapp.repository;


import com.lunarshade.vkapp.dto.userdao.UserInfo;
import com.lunarshade.vkapp.entity.CollectionType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
public class UserRepositoryTest extends Assertions {

    @Autowired
    UserRepository userRepository;

    @Test
    @Transactional
    void getBoardGameOwners() {
        List<UserInfo> owners = userRepository.getUsersByGameIdAndCollectionType(255, CollectionType.OWN);
//        owners.stream().map(UserView::getCollections).forEach(System.out::println);
        assertEquals(1, owners.size());
    }
}
