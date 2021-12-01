package com.lunarshade.vkapp.repository;

import com.lunarshade.vkapp.entity.BoardGameCollection;
import com.lunarshade.vkapp.entity.BoardGameCollectionId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardGameCollectionRepository extends JpaRepository<BoardGameCollection, BoardGameCollectionId> {

}
