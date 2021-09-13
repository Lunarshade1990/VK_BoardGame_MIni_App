package com.lunarshade.vkapp.repository;

import com.lunarshade.vkapp.dto.userdao.UserInfo;
import com.lunarshade.vkapp.entity.AppUser;
import com.lunarshade.vkapp.entity.CollectionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<AppUser, Long> {

    AppUser getByProviderId(String id);

    @Query("select u from user u " +
            "inner join u.collections c " +
            "inner join c.boardGameCollection bgc " +
            "inner join bgc.boardGame b " +
            "where b.id = ?1 and c.collectionType = ?2")
    List<UserInfo> getUsersByGameIdAndCollectionType(long id, CollectionType type);

}