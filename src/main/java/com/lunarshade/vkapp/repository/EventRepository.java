package com.lunarshade.vkapp.repository;

import com.lunarshade.vkapp.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}