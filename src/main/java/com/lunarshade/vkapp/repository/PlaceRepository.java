package com.lunarshade.vkapp.repository;

import com.lunarshade.vkapp.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, Long> {
}