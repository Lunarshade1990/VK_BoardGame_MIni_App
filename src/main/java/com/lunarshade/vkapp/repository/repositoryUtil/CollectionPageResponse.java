package com.lunarshade.vkapp.repository.repositoryUtil;

import com.lunarshade.vkapp.dto.userdao.BoardGameInfo;
import com.lunarshade.vkapp.dto.userdao.CollectionDto;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;


@Data
public class CollectionPageResponse {

    int totalPages;
    long totalElements;
    List<BoardGameInfo> content;
    CollectionDto collectionInfo;


    public CollectionPageResponse(Page<BoardGameInfo> page, CollectionDto collectionDto) {

        totalPages = page.getTotalPages();
        totalElements = page.getTotalElements();
        content = page.getContent();
        this.collectionInfo = collectionDto;
    }
}
