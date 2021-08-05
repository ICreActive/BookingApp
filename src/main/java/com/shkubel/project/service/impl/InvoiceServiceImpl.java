package com.shkubel.project.service.impl;

import com.shkubel.project.exception.OrderNotFoundException;
import com.shkubel.project.models.entity.*;
import com.shkubel.project.models.repo.InvoiceRepository;
import com.shkubel.project.service.InvoiceService;
import com.shkubel.project.service.OrderService;
import com.shkubel.project.util.DateTimeParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InvoiceServiceImpl implements InvoiceService {


    private final InvoiceRepository invoiceRepository;
    private final OrderService orderService;

    @Autowired
    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, OrderServiceImpl orderService) {
        this.invoiceRepository = invoiceRepository;
        this.orderService = orderService;
    }

    @Transactional
    @Override
    public Invoice createInvoice(Room room, OrderUser orderUser, Seller seller) throws OrderNotFoundException {

        Invoice invInDb = invoiceRepository.findInvoiceByOrderUser(orderUser);
        if (invInDb == null) {
            Invoice invoice = new Invoice();
            invoice.setSeller(seller);
            invoice.setUser(orderUser.getUser());
            invoice.setOrderUser(orderUser);
            invoice.setRoom(room);
            invoice.setPaid(false);
            invoice.setCreatingDate(DateTimeParser.nowToString());
            invoice.setActive(true);
            invoiceRepository.save(invoice);
            orderUser.setInvoice(invoice);
            orderUser.setActive(false);
            orderService.updateOrder(orderUser);
            return invoice;
        }
        return invInDb;
    }

    @Override
    public Invoice findInvoiceById(Long id) {
        return invoiceRepository.findInvoiceById(id);
    }

    @Override
    public List<Invoice> findAllByUser(User user) {
        return invoiceRepository.findAllByUser(user);
    }

    @Override
    public List<Invoice> findAll() {
        return invoiceRepository.findAll();
    }
}
