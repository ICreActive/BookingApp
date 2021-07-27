package com.shkubel.project.service;

import com.shkubel.project.models.entity.Room;
import com.shkubel.project.models.entity.KlassAppartament;
import com.shkubel.project.models.entity.OrderUser;

import java.time.LocalDate;
import java.util.List;


public interface RoomService {

    List<Room> findHotelByNumberOfSeats(Integer numberOfSeats);

    Room findHotelByDateFinishBetween(LocalDate start, LocalDate finish);

    List<Room> findHotelByKlassApartment(KlassAppartament klassAppartament);

    List<Room> findOffers(OrderUser orderUser);

    List<Room> findAllHotel();

    void saveHotel(Room room);

    Room findHotelById(Long id);

    Room createHotel();
}
