package com.shkubel.project.service.impl;

import com.shkubel.project.models.entity.Room;
import com.shkubel.project.models.entity.KlassAppartament;
import com.shkubel.project.models.entity.OrderUser;
import com.shkubel.project.models.repo.RoomRepository;
import com.shkubel.project.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public Room createHotel() {
        Room room = new Room();
        room.setNumberOfSeats(0);
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
    public List<Room> findAllHotel() {
        return roomRepository.findAll();
    }

    @Override
    public void saveHotel(Room room) {
        room.setAvailable(true);
        roomRepository.save(room);
    }

    @Override
    public Room findHotelById(Long id) {
        return roomRepository.findRoomsById(id);
    }


}

