package com.shkubel.project.service.impl;

import com.shkubel.project.exception.OrderNotFoundException;
import com.shkubel.project.models.entity.*;
import com.shkubel.project.models.repo.InvoiceRepository;
import com.shkubel.project.service.InvoiceService;
import com.shkubel.project.util.DateTimeParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class InvoiceServiceImpl implements InvoiceService {


    @Autowired
    InvoiceRepository invoiceRepository;
    @Autowired
    OrderServiceImpl orderService;

    @Transactional
    @Override
    public Invoice createInvoice(Hotel hotel, OrderUser orderUser, Seller seller) throws OrderNotFoundException {

        Invoice invInDb = invoiceRepository.findInvoiceByOrderUser(orderUser);
        if (invInDb == null) {
            Invoice invoice = new Invoice();
            invoice.setSeller(seller);
            invoice.setUser(orderUser.getUser());
            invoice.setOrderUser(orderUser);
            invoice.setHotel(hotel);
            invoice.setPaid(false);
            invoice.setCreatingDate(DateTimeParser.nowToString());
            invoice.setActive(true);
            Set<Invoice> hot = hotel.getInvoice();
            hot.add(invoice);
            invoiceRepository.save(invoice);
            orderUser.setInvoice(invoice);
            orderService.updateOrder(orderUser);
            return invoice;
        }
        return invInDb;
    }

    @Override
    public Invoice findInvoiceById(Long id) {
        return invoiceRepository.findInvoiceById(id);
    }

    public List<Invoice> findAllByUser(User user) {
        return invoiceRepository.findAllByUser(user);
    }
}
