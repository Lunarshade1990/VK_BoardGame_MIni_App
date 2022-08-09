package com.lunarshade.vkapp.repository;

import com.lunarshade.vkapp.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {
}