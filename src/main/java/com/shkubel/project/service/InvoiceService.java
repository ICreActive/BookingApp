package com.shkubel.project.service;

import com.shkubel.project.models.entity.Hotel;
import com.shkubel.project.models.entity.Invoice;
import com.shkubel.project.models.entity.OrderUser;
import com.shkubel.project.models.entity.Seller;

public interface InvoiceService {

    Invoice createInvoice (Hotel hotel, OrderUser orderUser, Seller seller);

    Invoice findInvoiceById(Long id);
}
