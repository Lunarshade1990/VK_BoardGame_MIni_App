package com.lunarshade.vkapp.repository;

import com.lunarshade.vkapp.entity.BoardGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BoardGameRepository extends JpaRepository<BoardGame, Long>{
}