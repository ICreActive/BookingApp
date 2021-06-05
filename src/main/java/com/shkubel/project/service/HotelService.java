package com.shkubel.project.service;

import com.shkubel.project.models.Hotel;
import com.shkubel.project.models.KlassAppartament;
import com.shkubel.project.models.OrderUser;
import com.shkubel.project.models.Seller;
import com.shkubel.project.repo.HotelRepository;
import com.shkubel.project.repo.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
public class HotelService {

    @Autowired
    HotelRepository hotelRepository;

//find

    public List<Hotel> findHotelByNumberOfSeats(int numberOfSeats) {
        return hotelRepository.findHotelByNumberOfSeats(numberOfSeats);
    }

    public Hotel findHotelByDateFinishBetween(LocalDate start, LocalDate finish) {
        return null;
    }

    public List<Hotel> findHotelByKlassApartment(KlassAppartament klassAppartament) {
        return hotelRepository.findHotelByKlassApartment(klassAppartament);
    }


    public Hotel findById(long id) {
        return null;
    }


    public List<Hotel> findOffers(OrderUser orderUser) {

        List<Hotel> hotelsWithNOS = findHotelByNumberOfSeats(orderUser.getNumberOfSeats());

            List <Hotel> hotelsOffers = hotelsWithNOS.stream().
                    filter(Objects::nonNull)
                    .filter(klass -> klass.getKlassApartment().equals(orderUser.getKlassOfApartment()))
                    .collect(Collectors.toList());
            return hotelsOffers;
        }

    public Iterable<Hotel> findAllHotel() {
        return hotelRepository.findAll();
    }

    public void saveHotel(Hotel hotel) {
        hotelRepository.save(hotel);
    }

    public Hotel findHotelById(long id) {
        return hotelRepository.findById(id);
    }



}

