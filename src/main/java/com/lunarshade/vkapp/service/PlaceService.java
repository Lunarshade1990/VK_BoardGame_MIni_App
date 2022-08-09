package com.lunarshade.vkapp.service;

import com.lunarshade.vkapp.dao.request.PlaceRequest;
import com.lunarshade.vkapp.dao.userdao.DeskInfo;
import com.lunarshade.vkapp.entity.AppUser;
import com.lunarshade.vkapp.entity.Place;
import com.lunarshade.vkapp.repository.DeskRepository;
import com.lunarshade.vkapp.repository.PlaceInfoRepository;
import com.lunarshade.vkapp.repository.UserRepository;
import com.lunarshade.vkapp.service.exceptions.ObjectExistsException;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Data
@Service
@Transactional(readOnly = true)
public class PlaceService {

    private final PlaceInfoRepository placeInfoRepository;
    private final DeskRepository deskRepository;
    private final UserRepository userRepository;
    private final DeskService deskService;

    public Place getPlaceIfExists(PlaceRequest form) {
        return form.publicPlace() ? getPublicPlaceIfExists(form.name(), form.address()) :
                getUserPlaceIfExists(userRepository.findById(form.userId()).get(), form.address());
    }

    public Place getUserPlaceIfExists(AppUser owner, String address) {
        return placeInfoRepository.getFirstByOwnerAndAddress(owner, address);
    }

    public Place getPublicPlaceIfExists(String name, String address) {
        return placeInfoRepository.getFirstByNameAndAddressAndPublicPlaceIsTrue(name, address);
    }

    public Set<Place> getAllByOwner(AppUser owner) {
        return placeInfoRepository.getAllByOwner(owner);
    }

    public Set<Place> getPublicPlaces() {
        return placeInfoRepository.getAllByPublicPlaceIsTrue();
    }

    @Transactional
    public Place savePlace(PlaceRequest placeForm) throws ObjectExistsException {
        AppUser user = userRepository.getById(placeForm.userId());
        Set<Place> places = user.getPlaces();
        checkIfPlaceExists(placeForm, places);
        return createNewPlace(user, placeForm);
    }

    public Set<DeskInfo> getPlaceDesks(Place place) {
        return deskRepository.getAllByPlace(place);
    }

    @Transactional
    public Place createNewPlace(AppUser user, PlaceRequest form) {
        Place place = new Place();
        placeInfoRepository.save(place);
        place.setAddress(form.address());
        place.setPublicPlace(form.publicPlace());
        place.setHome(form.home());
        place.setLatitude(form.latitude());
        place.setLongitude(form.longitude());
        if (form.publicPlace()) place.setCreator(user);
        else {
            user.getPlaces().add(place);
            place.setOwner(user);
        }
        return place;
    }

    public void checkIfPlaceExists(PlaceRequest placeForm, Set<Place> places) throws ObjectExistsException {
        boolean isAddrExist;
        if (!places.isEmpty()) {
            if (placeForm.publicPlace()) isAddrExist = isPublicPlaceExist(placeForm, places);
            else isAddrExist = isUserAddrExist(placeForm, places);
            if (isAddrExist) {
                throw new ObjectExistsException("Такой адрес уже есть у пользователя");
            }
        }
    }

    public boolean isUserAddrExist(PlaceRequest placeForm, Set<Place> places) {
        return places.stream()
                .anyMatch(p -> p.getAddress().equals(placeForm.address()));
    }

    public boolean isPublicPlaceExist(PlaceRequest placeForm, Set<Place> places) {
        return places.stream()
                .anyMatch(p -> p.getAddress().equals(placeForm.address()) && p.getName().equals(placeForm.name()));
    }
}
