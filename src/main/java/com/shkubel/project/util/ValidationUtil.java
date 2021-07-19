package com.shkubel.project.util;

import com.shkubel.project.models.entity.Hotel;
import com.shkubel.project.models.entity.KlassAppartament;
import com.shkubel.project.service.impl.HotelServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Locale;


@Service
public class ValidationUtil {

    @Autowired
    HotelServiceImpl hotelService;

    public static String validationHotel(Hotel hotel) {
        String message=null;
        for (KlassAppartament elem : KlassAppartament.values()) {
            if (!elem.getName().toLowerCase(Locale.ROOT).equals(hotel.getKlassApartment().getName().toLowerCase(Locale.ROOT))) {
               message = "This Klass of apartment is incorrect";
            }
        }
        if (message!=null) {
            message="success";
        }
        return message;
    }


    public boolean ValidationDate(LocalDate checkin, LocalDate checkout) {
        return !checkin.isAfter(checkout) && !checkin.equals(checkout);
    }

}

