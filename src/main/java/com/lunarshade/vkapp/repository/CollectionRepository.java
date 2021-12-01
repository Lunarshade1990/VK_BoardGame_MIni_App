package com.lunarshade.vkapp.repository;

import com.lunarshade.vkapp.entity.AppUser;
import com.lunarshade.vkapp.entity.Collection;
import com.lunarshade.vkapp.entity.CollectionType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectionRepository extends JpaRepository<Collection, Long> {

    int countByIdAndCollectionType(long id, String type);
    Collection findByAppUserAndCollectionType(AppUser user, CollectionType type);

}