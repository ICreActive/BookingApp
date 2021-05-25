package com.shkubel.project.repo;

import com.shkubel.project.models.Hotel;
import com.shkubel.project.models.OrderUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

    List<Hotel> findHotelByNumberOfSeats(int numberOfSeats);
    Hotel findHotelByKlassApartment(String klass);
    Hotel findHotelByDateFinishBetween(LocalDate start, LocalDate finish);

    Hotel findById(long id);


}
