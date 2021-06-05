package com.shkubel.project.util;

import com.shkubel.project.models.Hotel;
import com.shkubel.project.models.KlassAppartament;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Locale;

@Service
public class ValidationUtil {

    public static boolean validationHotel(Hotel hotel) {
        String message=null;
        for (KlassAppartament elem : KlassAppartament.values()
        ) {
            if (!elem.getName().toLowerCase(Locale.ROOT).equals(hotel.getKlassApartment().getName().toLowerCase(Locale.ROOT))) {
                message="This Klass of apartment is incorrect";
                return false;
            }
        }
        return true;
    }

    public boolean ValidationDate (LocalDate checkin, LocalDate checkout) {
        return !checkin.isAfter(checkout) && !checkin.equals(checkout);
    }
}

