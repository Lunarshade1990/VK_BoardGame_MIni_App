package com.lunarshade.vkapp.controller;

import com.lunarshade.vkapp.dao.BoardGameDao;
import com.lunarshade.vkapp.dao.CollectionDto;
import com.lunarshade.vkapp.dao.request.BoardGameFilter;
import com.lunarshade.vkapp.repository.BoardGameRepository;
import com.lunarshade.vkapp.repository.response.PageResponse;
import com.lunarshade.vkapp.repository.response.collection.CollectionPageResponse;
import com.lunarshade.vkapp.service.BoardGameService;
import com.lunarshade.vkapp.service.CollectionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/boardgames")
@CrossOrigin
public class BoardGameController {
    private final BoardGameRepository boardGameRepository;
    private final BoardGameService boardGameService;
    private final CollectionService collectionService;

    public BoardGameController(BoardGameRepository boardGameRepository, BoardGameService boardGameService, CollectionService collectionService) {
        this.boardGameRepository = boardGameRepository;
        this.boardGameService = boardGameService;
        this.collectionService = collectionService;
    }

    @GetMapping
    @ResponseBody
    public CollectionPageResponse getAllGamesWithFilter(
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) Integer page,
            BoardGameFilter filter) {

        size = size==null ? 10 : size;
        page = page==null ? 0 : page-1;

        CollectionDto collectionInfo = collectionService.getCollectionInfoByUserIdAndCollectionType(null, null);
        PageResponse<BoardGameDao> collectionPage = boardGameService
                .getBoardGamesByOwnerAndCollectionTypeWithFilter(null, null, filter, PageRequest.of(page, size));
        return new CollectionPageResponse(collectionPage, collectionInfo);
    }


}
