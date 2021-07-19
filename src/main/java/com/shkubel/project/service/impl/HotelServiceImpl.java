package com.shkubel.project.service.impl;

import com.shkubel.project.models.entity.Hotel;
import com.shkubel.project.models.entity.KlassAppartament;
import com.shkubel.project.models.entity.OrderUser;
import com.shkubel.project.models.repo.HotelRepository;
import com.shkubel.project.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    public Hotel createHotel() {
        Hotel hotel = new Hotel();
        hotel.setNumberOfSeats(0);
        hotel.setPrice(0);
        hotel.setAvailable(true);
        return hotel;
    }

    @Override
    public List<Hotel> findHotelByNumberOfSeats(Integer numberOfSeats) {
        return hotelRepository.findHotelByNumberOfSeats(numberOfSeats);
    }

    @Override
    public Hotel findHotelByDateFinishBetween(LocalDate start, LocalDate finish) {
        return null;
    }

    @Override
    public List<Hotel> findHotelByKlassApartment(KlassAppartament klassAppartament) {
        return hotelRepository.findHotelByKlassApartment(klassAppartament);
    }

    @Override
    public List<Hotel> findOffers(OrderUser orderUser) {

        List<Hotel> hotelsWithNOS =
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
                System.out.println(e.getMessage());
                return null;
            }
        }

        return hotelsWithNOS;
    }

    @Override
    public List<Hotel> findAllHotel() {
        return hotelRepository.findAll();
    }

    @Override
    public void saveHotel(Hotel hotel) {
        hotel.setAvailable(true);
        hotelRepository.save(hotel);
    }

    @Override
    public Hotel findHotelById(Long id) {
        return hotelRepository.findHotelById(id);
    }


}

