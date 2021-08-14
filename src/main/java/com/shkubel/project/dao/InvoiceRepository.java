package com.shkubel.project.dao;

import com.shkubel.project.models.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    List<Invoice> findAllByRoom(Room room);

    List<Invoice> findAllByUser(User user);

    List<Invoice> findAllBySeller(Seller seller);

    Invoice findInvoiceByOrderUser(OrderUser orderUser);

    Invoice findInvoiceById(Long id);

    @Query("select u from Invoice u where u.isActive=true")
    Invoice findInvoiceByActiveStatus ();

}
