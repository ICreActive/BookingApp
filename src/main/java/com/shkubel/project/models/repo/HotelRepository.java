package com.shkubel.project.models.repo;

import com.shkubel.project.models.entity.Hotel;
import com.shkubel.project.models.entity.KlassAppartament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {

    List<Hotel> findHotelByNumberOfSeats(Integer numberOfSeats);

    List<Hotel> findHotelByKlassApartment(KlassAppartament klassAppartament);

    Hotel findHotelById(Long id);

}
