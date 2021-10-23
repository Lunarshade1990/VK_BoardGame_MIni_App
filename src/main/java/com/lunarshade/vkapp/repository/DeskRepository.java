package com.lunarshade.vkapp.repository;

import com.lunarshade.vkapp.dao.userdao.DeskInfo;
import com.lunarshade.vkapp.entity.Desk;
import com.lunarshade.vkapp.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface DeskRepository extends JpaRepository<Desk, Long> {
    Set<DeskInfo> getAllByPlace(Place place);
}