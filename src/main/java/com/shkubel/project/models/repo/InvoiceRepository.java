package com.shkubel.project.models.repo;

import com.shkubel.project.models.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    List<Invoice> findAllByHotel(Hotel hotel);

    List<Invoice> findAllByUser(User user);

    List<Invoice> findAllBySeller(Seller seller);

    Invoice findInvoiceByOrderUser(OrderUser orderUser);

    Invoice findInvoiceById(Long id);

}
