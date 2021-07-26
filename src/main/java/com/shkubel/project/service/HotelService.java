package com.shkubel.project.service;

import com.shkubel.project.models.entity.Hotel;
import com.shkubel.project.models.entity.KlassAppartament;
import com.shkubel.project.models.entity.OrderUser;

import java.time.LocalDate;
import java.util.List;


public interface HotelService {

    List<Hotel> findHotelByNumberOfSeats(Integer numberOfSeats);

    Hotel findHotelByDateFinishBetween(LocalDate start, LocalDate finish);

    List<Hotel> findHotelByKlassApartment(KlassAppartament klassAppartament);

    List<Hotel> findOffers(OrderUser orderUser);

    List<Hotel> findAllHotel();

    void saveHotel(Hotel hotel);

    Hotel findHotelById(Long id);

    Hotel createHotel();
}
