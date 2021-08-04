package com.lunarshade.vkapp.repository;

import com.lunarshade.vkapp.entity.Desk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeskRepository extends JpaRepository<Desk, Long> {
}