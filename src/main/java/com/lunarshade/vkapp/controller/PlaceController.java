package com.lunarshade.vkapp.controller;


import com.lunarshade.vkapp.dao.request.PlaceRequest;
import com.lunarshade.vkapp.dao.request.TableForm;
import com.lunarshade.vkapp.dao.userdao.DeskInfo;
import com.lunarshade.vkapp.dao.userdao.PlaceDao;
import com.lunarshade.vkapp.entity.Place;
import com.lunarshade.vkapp.service.DeskService;
import com.lunarshade.vkapp.service.PlaceService;
import com.lunarshade.vkapp.service.UserService;
import com.lunarshade.vkapp.service.exceptions.ObjectExistsException;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/places")
@CrossOrigin
@Data
public class PlaceController {

    private final PlaceService placeService;
    private final UserService userService;
    private final DeskService deskService;

    @GetMapping("/public")
    @ResponseBody
    public Set<PlaceDao> getPublicPlaces() {
        return placeService.getPublicPlaces()
                .stream()
                .map(PlaceDao::new)
                .collect(Collectors.toSet());
    }

    @PostMapping("/{place}/tables")
    public ResponseEntity<Void> saveNewDesk(@PathVariable Place place, @RequestBody TableForm tableForm) {
        deskService.saveNewDesk(tableForm, place);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{place}/tables")
    public Set<DeskInfo> getPlaceDesks(@PathVariable Place place) {
        return  placeService.getPlaceDesks(place);
    }

    @PostMapping
    @ResponseBody
    public PlaceDao saveUserPlace(@RequestBody PlaceRequest form) {
        try {
            return new PlaceDao(placeService.savePlace(form));
        } catch (ObjectExistsException e) {
            System.out.println(e.getMessage());
            return new PlaceDao(placeService.getPlaceIfExists(form));
        }
    }
}
