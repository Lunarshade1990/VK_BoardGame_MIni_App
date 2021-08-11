package com.lunarshade.vkapp.dao.userdao;

import com.lunarshade.vkapp.entity.AppUser;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserDao {
    private final long id;
    private final String firstName;
    private final String secondName;
    private final String teseraProfile;
    private final String city;

    public UserDao(AppUser user) {
        id = user.getId();
        firstName = user.getFirstName();
        secondName = user.getSecondName();
        teseraProfile = user.getTeseraProfile();
        city = user.getCity().getName();
    }
}
