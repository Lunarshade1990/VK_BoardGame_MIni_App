package com.lunarshade.vkapp.dao.vkUser;

public record VkRequestUser(
        long id,
        String first_name,
        String last_name,
        String photo_100,
        short sex,
        String token,
        VkCity city
) {
}
