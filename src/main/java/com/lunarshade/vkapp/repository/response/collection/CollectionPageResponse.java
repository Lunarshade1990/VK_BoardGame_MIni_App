package com.lunarshade.vkapp.repository.response.collection;

import com.lunarshade.vkapp.dao.CollectionDto;
import com.lunarshade.vkapp.repository.response.PageResponse;
import lombok.Data;


@Data
public class CollectionPageResponse<T> {

    private final PageResponse<T> response;
    private final CollectionDto collectionInfo;
    public CollectionPageResponse(PageResponse<T> pageResponse, CollectionDto collectionDto) {
        collectionInfo = collectionDto;
        this.response = pageResponse;
    }
}
