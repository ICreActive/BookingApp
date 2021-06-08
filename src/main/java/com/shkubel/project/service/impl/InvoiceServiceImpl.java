package com.shkubel.project.service.impl;

import com.shkubel.project.models.entity.Hotel;
import com.shkubel.project.models.entity.Invoice;
import com.shkubel.project.models.entity.OrderUser;
import com.shkubel.project.models.entity.Seller;
import com.shkubel.project.models.repo.InvoiceRepository;
import com.shkubel.project.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    InvoiceRepository invoiceRepository;

    @Override
    public Invoice newInvoiceFromHotel(Hotel hotel, OrderUser orderUser, Seller seller) {
        Invoice invoice = new Invoice();
        invoice.setSeller(seller);
        invoice.setUser(orderUser.getUser());
        invoice.setHotel(hotel);
        return invoice;
    }


}
