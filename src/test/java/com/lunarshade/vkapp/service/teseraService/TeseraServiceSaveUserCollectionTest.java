package com.lunarshade.vkapp.service.teseraService;


import com.lunarshade.vkapp.entity.AppUser;
import com.lunarshade.vkapp.repository.UserRepository;
import com.lunarshade.vkapp.service.TeseraService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;



@TestPropertySource("classpath:application-test.properties")
public class TeseraServiceSaveUserCollectionTest {

    @Autowired
    TeseraService teseraService;
    @Autowired
    UserRepository userRepository;
    AppUser user;

//    @Before
//    static void getUser() {
//        user = userRepository.getByProviderId("465465466");
//    }

    @Test
    @Transactional
    void test() {
        user = userRepository.getByProviderId("465465466");
        teseraService.importCollection("lunarshade_test", user);
        assertThat(user.getCollections()).size().isEqualTo(1);
    }

}
