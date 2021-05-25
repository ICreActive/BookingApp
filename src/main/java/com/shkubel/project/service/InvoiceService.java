package com.shkubel.project.service;

import com.shkubel.project.models.*;
import com.shkubel.project.repo.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvoiceService {

    @Autowired
    InvoiceRepository invoiceRepository;

    public Invoice newInvoiceFromHotel (Hotel hotel, OrderUser orderUser, Seller seller) {
        Invoice invoice = new Invoice();
        invoice.setSeller(seller);
        invoice.setUser(orderUser.getUser());
        invoice.setHotel(hotel);
        return invoice;
    }




}
