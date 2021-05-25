package com.shkubel.project.repo;

import com.shkubel.project.models.Hotel;
import com.shkubel.project.models.Invoice;
import com.shkubel.project.models.Seller;
import com.shkubel.project.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice,Long> {

    Invoice findAllByHotel(Hotel hotel);
    Invoice findAllByUser (User user);
    Invoice findAllBySeller (Seller seller);

}
