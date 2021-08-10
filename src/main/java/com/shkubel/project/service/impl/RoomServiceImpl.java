package com.shkubel.project.service.impl;

import com.shkubel.project.exception.RoomNotFoundException;
import com.shkubel.project.log.InjectLogger;
import com.shkubel.project.models.entity.KlassAppartament;
import com.shkubel.project.models.entity.OrderUser;
import com.shkubel.project.models.entity.Room;
import com.shkubel.project.models.repo.RoomRepository;
import com.shkubel.project.service.RoomService;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
public class RoomServiceImpl implements RoomService {

    @InjectLogger
    private static Logger LOGGER;

    private final RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Room createRoom() {
        Room room = new Room();
        room.setNumberOfSeats(1);
        room.setPrice(0);
        room.setAvailable(true);
        return room;
    }


    @Override
    public List<Room> findHotelByNumberOfSeats(Integer numberOfSeats) {
        return roomRepository.findRoomsByNumberOfSeats(numberOfSeats);
    }

    @Override
    public Room findHotelByDateFinishBetween(LocalDate start, LocalDate finish) {
        return null;
    }

    @Override
    public List<Room> findHotelByKlassApartment(KlassAppartament klassAppartament) {
        return roomRepository.findRoomsByKlassApartment(klassAppartament);
    }

    @Override
    public List<Room> findOffers(OrderUser orderUser) {

        List<Room> hotelsWithNOS =
                findHotelByNumberOfSeats(orderUser.getNumberOfSeats())
                        .stream()
                        .filter(Objects::nonNull)
                        .filter(klass -> klass.getKlassApartment().equals(orderUser.getKlassOfApartment()))
                        .collect(Collectors.toList());
        if (!hotelsWithNOS.isEmpty()) {
            try {
                hotelsWithNOS.forEach(h -> {
                    if (!h.isAvailableOnDate(orderUser.getLocalDateStart(), orderUser.getLocalDateFinish())) {
                        hotelsWithNOS.remove(h);
                    }
                });

            } catch (ConcurrentModificationException e) {
                return null;
            }
        }

        return hotelsWithNOS;
    }

    @Override
    public List<Room> findAllRoom() {
        return roomRepository.findAll();
    }

    @Override
    public void saveRoom(Room room) {
        room.setAvailable(true);
        roomRepository.save(room);
        LOGGER.info("Room with title:{} successfully saved", room.getTitle());
    }

    @Override
    public Room findRoomById(Long id) {
        return roomRepository.findRoomsById(id);
    }

    @Override
    public void disableRoom(Long id) throws RoomNotFoundException {
        Room room;
        room = roomRepository.findRoomsById(id);
        if (room != null) {
            room.setAvailable(false);
            LOGGER.info("Room with id:{} successfully disabled", id);
        } else {
            LOGGER.info("Room with id:{} not found", id);
            throw new RoomNotFoundException("Room with id = " + id + "not found");
        }
    }

    @Override
    public List<Room> findAllEnableRoom() {
        return roomRepository.findRoomsByIsAvailableTrue();

    }

}

