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
public class SellerService {


    @Autowired
    SellerRepository sellerRepository;

    public Seller findSellerById(long id) {
        return sellerRepository.findSellerById(id);
    }

    public List<Seller> findAllSeller() {
        return sellerRepository.findAll();
    }


    public boolean saveSeller(Seller seller) {
        Seller sellerInDb = sellerRepository.findSellerByName(seller.getName());
        if (sellerInDb == null) {
            return true;
        }
        return false;
    }

}

