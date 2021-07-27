package com.shkubel.project.service.impl;

import com.shkubel.project.models.entity.Seller;
import com.shkubel.project.models.repo.SellerRepository;
import com.shkubel.project.service.SellerService;
import com.shkubel.project.util.DateTimeParser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public boolean saveSeller(Seller seller) {
        Seller sellerInDb = sellerRepository.findSellerByName(seller.getName());

        if (sellerInDb == null) {
            Seller s = new Seller();
            s.setAddress(seller.getAddress());
            s.setBankAccount(seller.getBankAccount());
            s.setName(seller.getName());
            s.setCreatingDate(DateTimeParser.nowToString());
            s.setActive(true);
            sellerRepository.findAll().forEach(sell -> sell.setActive(false));
            sellerRepository.save(s);
            return true;
        }
        return false;
    }


    @Override
    @Transactional
    public void update(Long id, Seller seller) {
        if (seller.getId().equals(id)) {
            if (sellerRepository.findById(id).isPresent()) {
                Seller sellerInDB = sellerRepository.findById(id).get();
                sellerInDB.setName(seller.getName());
                sellerInDB.setBankAccount(seller.getBankAccount());
                sellerInDB.setAddress(seller.getAddress());
                sellerInDB.setUpdatingDate(DateTimeParser.nowToString());
                saveSeller(sellerInDB);
            }

        }


    }

    @Override
    public Seller findSellerByActiveStatus() {
        return sellerRepository.findSellerByActiveStatus();
    }

}



