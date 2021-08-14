package com.shkubel.project.util;

import com.shkubel.project.exception.AppartmentValidationException;
import com.shkubel.project.exception.DateValidationException;
import com.shkubel.project.models.entity.Room;
import com.shkubel.project.models.entity.KlassAppartament;
import com.shkubel.project.service.impl.RoomServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Locale;


@Component
public class ValidationUtil {

    @Autowired
    RoomServiceImpl hotelService;

    public static void validationHotel(Room room) throws AppartmentValidationException {

        boolean klassPresent = false;
        for (KlassAppartament elem : KlassAppartament.values()) {
            if (elem.getName().toLowerCase(Locale.ROOT).equals(room.getKlassApartment().getName().toLowerCase(Locale.ROOT))) {
                klassPresent = true;
                break;
            }
        }
        if (!klassPresent) {
            throw new AppartmentValidationException("This Klass of apartment is incorrect");
        }
    }


    public void validationDate(LocalDate checkin, LocalDate checkout) throws DateValidationException {

        if (checkin.isBefore(LocalDate.now())) {
            throw new DateValidationException("Check-in date cannot be earlier than the current date");
        }
        if (checkin.isAfter(checkout)) {
            throw new DateValidationException("Check-in date cannot be later than the check-out");
        }
        if (checkin.equals(checkout)) {
            throw new DateValidationException("Check-in date cannot be equal to check-out date");
        }
    }

}

