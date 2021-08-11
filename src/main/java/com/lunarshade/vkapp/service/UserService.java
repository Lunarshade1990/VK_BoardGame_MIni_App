package com.lunarshade.vkapp.service;

import com.lunarshade.vkapp.dao.vkUser.VkRequestUser;
import com.lunarshade.vkapp.entity.AppUser;
import com.lunarshade.vkapp.entity.City;
import com.lunarshade.vkapp.entity.Roles;
import com.lunarshade.vkapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public AppUser getByProviderId(String id) {
        return repository.getByProviderId(id);
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
        return  repository.save(appUser);
    }


}
