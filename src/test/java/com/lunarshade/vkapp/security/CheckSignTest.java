package com.lunarshade.vkapp.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;

@SpringBootTest
public class CheckSignTest extends Assertions {
    static MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
    @Autowired
    SignChecker signChecker;

    @BeforeAll
    static void initRequest() {
        mockHttpServletRequest.addHeader("authorization", "yB5IGFgxL00K-i0KAxVBO5GoB_U2XwDD833_zSbQ7rk");
        mockHttpServletRequest.addHeader("vk_access_token_settings", "");
        mockHttpServletRequest.addHeader("vk_app_id", "7908166");
        mockHttpServletRequest.addHeader("vk_are_notifications_enabled", "0");
        mockHttpServletRequest.addHeader("vk_is_app_user", "1");
        mockHttpServletRequest.addHeader("vk_is_favorite", "0");
        mockHttpServletRequest.addHeader("vk_language", "ru");
        mockHttpServletRequest.addHeader("vk_platform", "desktop_web");
        mockHttpServletRequest.addHeader("vk_ref", "other");
        mockHttpServletRequest.addHeader("vk_ts", "1628067068");
        mockHttpServletRequest.addHeader("vk_user_id", "85384889");
    }

    @Test
    void checkTest() throws Exception {
        assertTrue(signChecker.check(mockHttpServletRequest));
    }


}
