package com.shkubel.project.service;

import com.shkubel.project.exception.SellerNotFoundException;
import com.shkubel.project.models.entity.Seller;

import java.util.List;

public interface SellerService {

    Seller findSellerById(Long id);

    List<Seller> findAllSeller();

    void saveSeller(Seller seller) throws SellerNotFoundException;

    void update(Long id, Seller seller) throws SellerNotFoundException;

    Seller findSellerByActiveStatus() throws SellerNotFoundException;

    void setActiveSeller(Long id);
}
