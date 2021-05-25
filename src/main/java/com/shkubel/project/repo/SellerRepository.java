package com.shkubel.project.repo;

import com.shkubel.project.models.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller, Long> {

    Seller findSellerById(long id);
    Seller findSellerByName(String name);

}
