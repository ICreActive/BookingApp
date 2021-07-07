package com.shkubel.project.service.impl;

import com.shkubel.project.models.entity.Seller;
import com.shkubel.project.models.repo.SellerRepository;
import com.shkubel.project.service.SellerService;
import com.shkubel.project.util.DateTimeParser;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class SellerServiceImpl implements SellerService {


    final
    SellerRepository sellerRepository;

    public SellerServiceImpl(SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

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
        return sellerInDb == null;
    }

    @Override
    public void update(Long id, Seller seller) {

        if (seller.getId() == id) {
            if (sellerRepository.findById(id).isPresent()) {
                Seller sellerInDB = sellerRepository.findById(id).get();
                sellerInDB.setUpdatingDate(DateTimeParser.parseToString(LocalDateTime.now()));
                saveSeller(sellerInDB);
            }

        }
    }

}

