package com.lunarshade.vkapp.service;

import com.lunarshade.vkapp.entity.AppUser;
import com.lunarshade.vkapp.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository repository;


    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public AppUser getByProviderId(String id) {
        return repository.getByProviderId(id);
    }


}
