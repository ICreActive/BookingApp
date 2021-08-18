package com.shkubel.project.service.impl;

import com.shkubel.project.exception.SellerNotFoundException;
import com.shkubel.project.log.InjectLogger;
import com.shkubel.project.models.entity.Seller;
import com.shkubel.project.dao.SellerRepository;
import com.shkubel.project.service.SellerService;
import com.shkubel.project.util.DateTimeParser;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SellerServiceImpl implements SellerService {

    @InjectLogger
    private static Logger LOGGER;

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

    /**
     * Creating new Seller.
     * When a seller is created he is assigned the status "Active = true" and the rest - Active = false "
     * @param seller - seller from view to save
     * @throws SellerNotFoundException if seller is exist
     */
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
        LOGGER.info("Seller with name {} has been saved ", seller.getName());
    }


    @Override
    @Transactional
    public void update(Long id, Seller seller) throws SellerNotFoundException {
        if (seller.getId().equals(id)) {
            Seller sellerInDB = sellerRepository.findById(id).orElseThrow(() -> new SellerNotFoundException("Seller not found"));
            sellerInDB.setName(seller.getName());
            sellerInDB.setBankAccount(seller.getBankAccount());
            sellerInDB.setAddress(seller.getAddress());
            sellerInDB.setUpdatingDate(DateTimeParser.nowToString());
            saveSeller(sellerInDB);
            LOGGER.info("Seller with name {} has been updated ", seller.getName());
        }
    }

    @Override
    public Seller findSellerByActiveStatus() throws SellerNotFoundException {

        Seller seller = sellerRepository.findSellerByActiveStatus();
        if (seller != null)
            return seller;
        else {
            LOGGER.info("There are no active sellers in the database");
            throw new SellerNotFoundException("Seller not found");
        }

    }

    /**
     * One seller may be in "Active" status
     * @param id by active Seller
     */
    @Transactional
    @Override
    public void setActiveSeller(Long id) {
        Seller seller = findSellerById(id);
        seller.setActive(true);
        sellerRepository.save(seller);

        sellerRepository.findAll().stream()
                .filter(s -> !s.getId().equals(id))
                .forEach(s -> s.setActive(false));
        LOGGER.info("The status of all sellers has been updated successfully");
    }

}



