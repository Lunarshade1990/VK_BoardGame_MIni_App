package com.lunarshade.vkapp.service;


import com.lunarshade.vkapp.dao.request.TableForm;
import com.lunarshade.vkapp.entity.Desk;
import com.lunarshade.vkapp.entity.Place;
import com.lunarshade.vkapp.repository.DeskRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class DeskService {
    private final DeskRepository deskRepository;

    public void deleteTable(long id) {
        deskRepository.deleteById(id);
    }

    public Desk updateDesk(TableForm tableForm) {
        Desk desk = deskRepository.findById(tableForm.getId()).get();
        desk.setName(tableForm.getName());
        desk.setMaxPlayersNumber(tableForm.getMax());
        desk.setLength(tableForm.getLength());
        desk.setWidth(tableForm.getWidth());
        desk.setDeskShape(tableForm.getShape());
        return deskRepository.save(desk);
    }

    public Desk saveNewDesk(TableForm tableForm, Place place) {
        Desk desk = new Desk();
        desk.setName(tableForm.getName());
        desk.setMaxPlayersNumber(tableForm.getMax());
        desk.setLength(tableForm.getLength());
        desk.setWidth(tableForm.getWidth());
        desk.setDeskShape(tableForm.getShape());
        desk.setPlace(place);
        desk.setByDefault(tableForm.isByDefault());
        return deskRepository.save(desk);
    }

    public void changeDefaultDesk(Desk desk) {
        Place place = desk.getPlace();
        place.getTables().stream()
                .filter(d -> d.getId() != desk.getId())
                .forEach(d -> d.setByDefault(false));
        desk.setByDefault(true);
        deskRepository.saveAll(place.getTables());
    }

}
