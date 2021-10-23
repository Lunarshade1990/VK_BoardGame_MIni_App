package com.lunarshade.vkapp.service;

import com.lunarshade.vkapp.dao.request.PlaceRequest;
import com.lunarshade.vkapp.dao.request.TableForm;
import com.lunarshade.vkapp.dao.userdao.DeskInfo;
import com.lunarshade.vkapp.entity.AppUser;
import com.lunarshade.vkapp.entity.Desk;
import com.lunarshade.vkapp.entity.Place;
import com.lunarshade.vkapp.repository.DeskRepository;
import com.lunarshade.vkapp.repository.PlaceInfoRepository;
import com.lunarshade.vkapp.repository.UserRepository;
import com.lunarshade.vkapp.service.exceptions.ObjectExistsException;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.Set;

@Data
@Service
public class PlaceService {

    private final PlaceInfoRepository placeInfoRepository;
    private final DeskRepository deskRepository;
    private final UserRepository userRepository;

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

    public Place savePlace(PlaceRequest placeForm) throws ObjectExistsException {
        AppUser user = userRepository.getById(placeForm.userId());
        Set<Place> places = user.getPlaces();
        checkIfPlaceExists(placeForm, places);
        Place place = createNewPlace(user, placeForm);
        place = placeInfoRepository.save(place);
        userRepository.save(user);
        return place;
    }

    public Desk saveTable(Place place, TableForm tableForm) {
        Desk desk = new Desk();
        desk.setName(tableForm.getName());
        desk.setMaxPlayersNumber(tableForm.getMax());
        desk.setLength(tableForm.getLength());
        desk.setWidth(tableForm.getWidth());
        desk.setDeskShape(tableForm.getShape());
        desk.setPlace(place);
        return deskRepository.save(desk);
    }

    public Set<DeskInfo> getPlaceDesks(Place place) {
        Set<DeskInfo> deskInfos = deskRepository.getAllByPlace(place);
        return deskInfos;
    }

    private Place createNewPlace(AppUser user, PlaceRequest form) {
        Place place = new Place();
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

    private void checkIfPlaceExists(PlaceRequest placeForm, Set<Place> places) throws ObjectExistsException {
        boolean isAddrExist;
        if (!places.isEmpty()) {
            if (placeForm.publicPlace()) isAddrExist = isPublicPlaceExist(placeForm, places);
            else isAddrExist = isUserAddrExist(placeForm, places);
            if (isAddrExist) {
                throw new ObjectExistsException("Такой адрес уже есть у пользователя");
            };
        }
    }

    private boolean isUserAddrExist(PlaceRequest placeForm, Set<Place> places) {
        boolean isAddrExist = places.stream()
                .anyMatch(p -> p.getAddress().equals(placeForm.address()));
        return isAddrExist;
    }

    private boolean isPublicPlaceExist(PlaceRequest placeForm, Set<Place> places) {
        boolean isAddrExist = places.stream()
                .anyMatch(p -> p.getAddress().equals(placeForm.address()) && p.getName().equals(placeForm.name()));
        return isAddrExist;
    }
}
