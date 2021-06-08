package com.shkubel.project.service;

import com.shkubel.project.models.entity.Seller;
import java.util.List;

public interface SellerService {

    Seller findSellerById(Long id);
    List<Seller> findAllSeller();
    Boolean saveSeller(Seller seller);

}
