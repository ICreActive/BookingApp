package com.shkubel.project.service.impl;

import com.shkubel.project.exception.SellerNotFoundException;
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
    public void saveSeller(Seller seller) throws SellerNotFoundException {

        Seller sellerInDb = sellerRepository.findSellerByName(seller.getName());
        if (sellerInDb != null) {
            throw new SellerNotFoundException("Seller with the same name already exists");
        }
        Seller s = new Seller();
        s.setAddress(seller.getAddress());
        s.setBankAccount(seller.getBankAccount());
        s.setName(seller.getName());
        s.setCreatingDate(DateTimeParser.nowToString());
        s.setActive(true);
        sellerRepository.findAll().forEach(sell -> sell.setActive(false));
        sellerRepository.save(s);
    }


    @Override
    @Transactional
    public void update(Long id, Seller seller) throws SellerNotFoundException {
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
    public Seller findSellerByActiveStatus() throws SellerNotFoundException {

        Seller seller = sellerRepository.findSellerByActiveStatus();
        if (seller != null)
            return seller;
        else {
            throw new SellerNotFoundException("Seller not found");
        }

    }

    @Transactional
    @Override
    public void setActiveSeller(Long id) {

        Seller seller = findSellerById(id);
        seller.setActive(true);
        sellerRepository.save(seller);

        sellerRepository.findAll().stream()
                .filter(s -> !s.getId().equals(id))
                .forEach(s -> s.setActive(false));
    }

}



