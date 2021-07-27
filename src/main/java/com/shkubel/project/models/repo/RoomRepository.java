package com.shkubel.project.models.repo;

import com.shkubel.project.models.entity.Room;
import com.shkubel.project.models.entity.KlassAppartament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findRoomsByNumberOfSeats(Integer numberOfSeats);

    List<Room> findRoomsByKlassApartment(KlassAppartament klassAppartament);

    Room findRoomsById(Long id);

}
