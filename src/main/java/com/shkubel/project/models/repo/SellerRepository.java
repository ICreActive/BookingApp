package com.shkubel.project.models.repo;

import com.shkubel.project.models.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {

    Seller findSellerById(Long id);

    Seller findSellerByName(String name);

    Seller findSellerByIsActive(Boolean active);

}
