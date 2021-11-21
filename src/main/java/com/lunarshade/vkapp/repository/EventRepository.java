package com.lunarshade.vkapp.repository;

import com.lunarshade.vkapp.entity.AppUser;
import com.lunarshade.vkapp.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findAllByCreatorAndStartDateAfter(AppUser user, Date date);
}