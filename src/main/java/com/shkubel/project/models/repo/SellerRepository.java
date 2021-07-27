package com.shkubel.project.models.repo;

import com.shkubel.project.models.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {

    Seller findSellerById(Long id);

    Seller findSellerByName(String name);

    @Query("select u from Seller u where u.isActive=true")
    Seller findSellerByActiveStatus();

}
