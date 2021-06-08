package com.shkubel.project.models.repo;

import com.shkubel.project.models.entity.Hotel;
import com.shkubel.project.models.entity.Invoice;
import com.shkubel.project.models.entity.Seller;
import com.shkubel.project.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice,Long> {

    Invoice findAllByHotel(Hotel hotel);
    Invoice findAllByUser (User user);
    Invoice findAllBySeller (Seller seller);

}
