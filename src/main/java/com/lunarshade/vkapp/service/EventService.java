package com.lunarshade.vkapp.service;

import com.lunarshade.vkapp.dao.request.gameevent.GameEventRqDto;
import com.lunarshade.vkapp.dao.request.gameevent.PlayRqDto;
import com.lunarshade.vkapp.entity.*;
import com.lunarshade.vkapp.repository.*;
import lombok.RequiredArgsConstructor;

import java.util.Calendar;
import java.util.Optional;

@RequiredArgsConstructor
public class EventService {

    UserRepository userRepository;
    BoardGameRepository boardGameRepository;
    PlaceInfoRepository placeRepository;
    DeskRepository deskRepository;
    EventRepository eventRepository;
    PlayRepository playRepository;

    public Event saveNewEvent(GameEventRqDto gameEventRqDto) {
        PlayRqDto playRqDto = gameEventRqDto
                .tables().get(0)
                .plays().get(0);
        Play play = new Play();
        BoardGame boardGame = boardGameRepository.findById(playRqDto.game()).get();
        AppUser creator = userRepository.findById(gameEventRqDto.creator()).get();
        AppUser host = userRepository.findById(playRqDto.host()).get();
        Place place = placeRepository.findById(gameEventRqDto.place()).get();
        Desk eventDesk = getDeskForEvent(gameEventRqDto, place);
        Event event = new Event();
        event.setPlace(place);
        event.setCreator(creator);
        fillPlayInfo(play, playRqDto, boardGame, host, eventDesk);
        play.setEvent(event);
        eventDesk.getPlays().add(play);
        event.getPlays().add(play);
        event = eventRepository.save(event);
        return event;
    }

    public Play addNewPlay(Event event, PlayRqDto playRqDto) {
        Play play = new Play();
        BoardGame boardGame = boardGameRepository.findById(playRqDto.game()).get();
        AppUser host = userRepository.findById(playRqDto.host()).get();
        Desk desk = deskRepository.getById(playRqDto.tableId());
        fillPlayInfo(play, playRqDto, boardGame, host, desk);
        play.setEvent(event);
        return playRepository.save(play);
    }



    public void updatePlay(Play play, PlayRqDto playRqDto) {
        BoardGame boardGame = boardGameRepository.findById(playRqDto.game()).get();
        AppUser host = userRepository.findById(playRqDto.host()).get();
        Desk desk = deskRepository.getById(playRqDto.tableId());
        fillPlayInfo(play, playRqDto, boardGame, host, desk);
        playRepository.save(play);
    }

    private void fillPlayInfo(Play play, PlayRqDto playRqDto, BoardGame boardGame, AppUser host, Desk desk) {
        PlayTime playTime = new PlayTime();
        playTime.setTimeStart(playRqDto.timeFrom());
        playTime.setTimeEnd(playRqDto.timeTo());
        play.setBoardGame(boardGame);
        play.setComment(playRqDto.comment());
        play.setPlannedTime(playTime);
        play.setPlayerMinCount(playRqDto.playersFrom());
        play.setPlayerMaxCount(playRqDto.playersTo());
        play.setHost(host);
        play.setTable(desk);
        playRqDto.players()
                .stream()
                .map(id -> userRepository.findById(id).get())
                .forEach(play.getPlayers()::add);
        if (playRqDto.virtualPlayers() != null) {
            playRqDto.virtualPlayers().forEach(play.getVirtualUsers()::add);
        }

    }

    public void setEventLastUpdateTime(Event event, Calendar date) {
        event.setLastUpdateTime(date);
        eventRepository.save(event);
    }

    public void addNewUser(Play play, AppUser user) throws Exception {
        if (play.haveSeats()) {
            user.getPlays().add(play);
            play.getPlayers().add(user);
            playRepository.save(play);
        } else throw new Exception("Нет свободных мест");
    }

    public void addNewVirtualUser(Play play, String user) throws Exception {
        if (play.haveSeats()) {
            play.getVirtualUsers().add(user);
            playRepository.save(play);
        } else throw new Exception("Нет свободных мест");
    }


    private Desk getDeskForEvent (GameEventRqDto gameEventRqDto, Place place) {
        if (gameEventRqDto.type().equals("simple")) {
            return getDeskForSimpleEvent(place);
        } else {
            return deskRepository.getById(gameEventRqDto.tables().get(0).id());
        }
    }

    private Desk getDeskForSimpleEvent (Place place) {
        return getDefaultDesk(place).orElse(getVirtualDeskForPlace(place));
    }

    private Optional<Desk> getDefaultDesk (Place place) {
        if (place.getTables().size() < 1) {
            return Optional.empty();
        } else {
            return place.getTables()
                    .stream()
                    .filter(table -> table.isByDefault())
                    .findFirst();
        }
    }

    private Desk getVirtualDeskForPlace(Place place) {
        Desk desk = new Desk();
        desk.setDeskType(DeskType.VIRTUAL);
        desk.setPlace(place);
        place.getTables().add(desk);
        placeRepository.save(place);
        return desk;
    }


}
