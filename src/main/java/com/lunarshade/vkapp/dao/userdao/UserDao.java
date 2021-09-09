package com.lunarshade.vkapp.dao.userdao;

import com.lunarshade.vkapp.entity.AppUser;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserDao {
    private final long id;
    private final String vkId;
    private final String firstName;
    private final String secondName;
    private final String teseraProfile;
    private final String city;
    private final String avatar;

    public UserDao(long id, String vkId, String firstName, String secondName, String teseraProfile, String city, String avatar) {
        this.id = id;
        this.vkId = vkId;
        this.firstName = firstName;
        this.secondName = secondName;
        this.teseraProfile = teseraProfile;
        this.city = city;
        this.avatar = avatar;
    }

    public UserDao(AppUser user) {
        id = user.getId();
        firstName = user.getFirstName();
        vkId = user.getProviderId();
        secondName = user.getSecondName();
        teseraProfile = user.getTeseraProfile();
        city = user.getCity().getName();
        avatar = user.getAvatarUrl();
    }
}
