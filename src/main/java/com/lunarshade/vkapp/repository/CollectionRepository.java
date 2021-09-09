package com.lunarshade.vkapp.repository;

import com.lunarshade.vkapp.entity.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectionRepository extends JpaRepository<Collection, Long> {

    int countByIdAndCollectionType(long id, String type);

}