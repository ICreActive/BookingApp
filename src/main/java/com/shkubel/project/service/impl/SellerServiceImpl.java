package com.shkubel.project.service.impl;

import com.shkubel.project.models.entity.Seller;
import com.shkubel.project.models.repo.SellerRepository;
import com.shkubel.project.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SellerServiceImpl implements SellerService {


    @Autowired
    SellerRepository sellerRepository;

    @Override
    public Seller findSellerById(Long id) {
        return sellerRepository.findSellerById(id);
    }

    @Override
    public List<Seller> findAllSeller() {
        return sellerRepository.findAll();
    }

    @Override
    public Boolean saveSeller(Seller seller) {
        Seller sellerInDb = sellerRepository.findSellerByName(seller.getName());
        if (sellerInDb == null) {
            return true;
        }
        return false;
    }

}

