package com.lunarshade.vkapp.repository;

import com.lunarshade.vkapp.entity.BoardGame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardGameRepository extends JpaRepository<BoardGame, Long>{

}