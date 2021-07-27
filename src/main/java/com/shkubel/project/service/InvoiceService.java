package com.shkubel.project.service;

import com.shkubel.project.exception.OrderNotFoundException;
import com.shkubel.project.models.entity.*;

import java.util.List;

public interface InvoiceService {

    Invoice createInvoice (Room room, OrderUser orderUser, Seller seller) throws OrderNotFoundException;

    Invoice findInvoiceById(Long id);

    List<Invoice> findAllByUser(User user);

    List<Invoice> findAll();
}
