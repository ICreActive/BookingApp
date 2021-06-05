package com.shkubel.project.repo;

import com.shkubel.project.models.Hotel;
import com.shkubel.project.models.KlassAppartament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {

    List<Hotel> findHotelByNumberOfSeats(int numberOfSeats);
    Hotel findHotelByDateFinishBetween(LocalDate start, LocalDate finish);
    List<Hotel> findHotelByKlassApartment(KlassAppartament klassAppartament);

    Hotel findById(long id);

}
