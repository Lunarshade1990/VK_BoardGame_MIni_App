package com.lunarshade.vkapp.repository;

import com.lunarshade.vkapp.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AppUser, Long> {

    AppUser getByProviderId(String id);
}