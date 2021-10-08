package com.lunarshade.vkapp.dao;

import com.lunarshade.vkapp.entity.AppUser;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDao {
    private final long id;
    private final String providerId;
    private final String firstName;
    private final String secondName;
    private final String teseraProfile;
    private final String avatarUrl;

    public UserDao(AppUser user) {
        this.id = user.getId();
        this.providerId = user.getProviderId();
        this.firstName = user.getFirstName();
        this.secondName = user.getSecondName();
        this.teseraProfile = user.getTeseraProfile();
        this.avatarUrl = user.getAvatarUrl();
    }
}
