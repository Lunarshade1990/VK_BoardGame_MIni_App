package com.lunarshade.vkapp.repository.response;

import lombok.Data;

import java.util.Collections;
import java.util.List;


@Data
public class PageResponse<T> {
    private List<T> content;
    private long totalElements;
    private long totalPages;

    public PageResponse(List<T> content, long totalElements, long pageSize) {
        this.content = content;
        this.totalElements = totalElements;
        if (pageSize == 0) {
            this.totalPages = 0;
        } else this.totalPages = (int) Math.ceil((double) totalElements / pageSize);
    }

    public static PageResponse emptyList() {
        return new PageResponse(Collections.emptyList(),0, 0);
    }
}
