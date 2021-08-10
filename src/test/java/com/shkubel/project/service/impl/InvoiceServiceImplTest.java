package com.shkubel.project.service.impl;

import com.shkubel.project.exception.OrderNotFoundException;
import com.shkubel.project.models.entity.*;
import com.shkubel.project.models.repo.InvoiceRepository;
import com.shkubel.project.service.InvoiceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class InvoiceServiceImplTest {

    @MockBean
    private InvoiceRepository invoiceRepository;

    @Autowired
    private InvoiceService invoiceService;

    private Invoice invoice;
    private OrderUser order;
    private Seller seller;
    private Room room;
    private User user;

    @BeforeEach
    public void setUp() {
        invoice = new Invoice();
        order = new OrderUser();
        seller = new Seller();
        room = new Room();

    }

    @Test
    void createInvoice() throws OrderNotFoundException {

        invoiceService.createInvoice(room, order, seller);
        Mockito.verify(invoiceRepository, Mockito.times(1)).save(invoice);
    }

    @Test
    void findInvoiceById() {
        invoiceService.findInvoiceById(1L);
        Mockito.verify(invoiceRepository, Mockito.times(1)).findInvoiceById(1L);
    }

    @Test
    void findAllByUser() {
        invoiceService.findAllByUser(user);
        Mockito.verify(invoiceRepository, Mockito.times(1)).findAllByUser(user);
    }

    @Test
    void findAll() {
        invoiceService.findAll();
        Mockito.verify(invoiceRepository, Mockito.times(1)).findAll();
    }
}