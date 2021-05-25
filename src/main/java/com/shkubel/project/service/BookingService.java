package com.shkubel.project.service;

import com.shkubel.project.models.Hotel;
import com.shkubel.project.models.OrderUser;
import com.shkubel.project.models.Seller;
import com.shkubel.project.repo.HotelRepository;
import com.shkubel.project.repo.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BookingService {

    @Autowired
    HotelRepository hotelRepository;
    @Autowired
    SellerRepository sellerRepository;

//find

    public List<Hotel> findHotelByNumberOfSeats(int numberOfSeats) {
        return hotelRepository.findHotelByNumberOfSeats(numberOfSeats);
    }

    public List<Hotel> findOffers(OrderUser orderUser) {
        List<Hotel> hotels = findHotelByNumberOfSeats(orderUser.getNumberOfSeats());
        return hotels;
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

    public Seller findSellerById(long id) {
        return sellerRepository.findSellerById(id);
    }

    public List<Seller> findAllSeller() {
        return sellerRepository.findAll();
    }

//save

    public boolean saveSeller(Seller seller) {
        Seller sellerInDb = sellerRepository.findSellerByName(seller.getName());
        if (sellerInDb == null) {
            return true;
        }
        return false;
    }

    //logic



}

