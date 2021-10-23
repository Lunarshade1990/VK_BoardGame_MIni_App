package com.lunarshade.vkapp.repository;

import com.lunarshade.vkapp.entity.AppUser;
import com.lunarshade.vkapp.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Set;

public interface PlaceInfoRepository extends JpaRepository<Place, Long>, QuerydslPredicateExecutor {
    Place getFirstByOwnerAndAddress(AppUser owner, String address);
    Place getFirstByNameAndAddressAndPublicPlaceIsTrue(String name, String address);
    Set<Place> getAllByOwner(AppUser owner);
    Set<Place> getAllByPublicPlaceIsTrue();
}