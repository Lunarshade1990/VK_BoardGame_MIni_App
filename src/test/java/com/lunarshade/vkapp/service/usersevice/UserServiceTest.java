package com.lunarshade.vkapp.service.usersevice;

import com.lunarshade.vkapp.dao.vkUser.VkCity;
import com.lunarshade.vkapp.dao.vkUser.VkRequestUser;
import com.lunarshade.vkapp.entity.AppUser;
import com.lunarshade.vkapp.repository.UserRepository;
import com.lunarshade.vkapp.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
@Rollback
public class UserServiceTest {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @Test
    @Transactional
    void saveUserFromVkMiniAppTest() {
        VkRequestUser request = new VkRequestUser(
                465465469L,
                "Inga",
                "Kuksova",
                "photo_url",
                (short) 0,
                "token",
                new VkCity(1L, "Moscow")
        );

        AppUser appUser = userService.saveUserFromVkMiniApp(request);
        System.out.println(appUser);
        AppUser appUser1 = userRepository.getByProviderId("465465469");
        assertThat(appUser1).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(appUser);
    }
}
