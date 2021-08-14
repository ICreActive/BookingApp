package com.shkubel.project.service.impl;

import com.shkubel.project.exception.SellerNotFoundException;
import com.shkubel.project.models.entity.Seller;
import com.shkubel.project.dao.SellerRepository;
import com.shkubel.project.service.SellerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest
class SellerServiceImplTest {

    @Autowired
    private SellerService sellerService;

    @MockBean
    private SellerRepository sellerRepository;

    private Seller seller;

    @BeforeEach
    public void setUp() {
        seller = new Seller();
        seller.setName("Yuri");
        seller.setAddress("Prod street");
        seller.setBankAccount("123");
        seller.setId(1L);
    }


    @Test
    void findSellerByIdTest() {
        Mockito.doReturn(seller)
                .when(sellerRepository)
                .findSellerById(seller.getId());
        sellerService.findSellerById(seller.getId());
        Mockito.verify(sellerRepository, Mockito.times(1)).findSellerById(seller.getId());
    }

    @Test
    void findAllSellerTest() {
        Mockito.doReturn(new ArrayList<>())
                .when(sellerRepository)
                .findAll();
        sellerService.findAllSeller();
        Mockito.verify(sellerRepository, Mockito.times(1)).findAll();
    }

    @Test
    void saveSellerThrowSellerNotFoundExceptionTest() {
        Mockito.doReturn(seller)
                .when(sellerRepository)
                .findSellerByName(seller.getName());
        Assertions.assertThrows(SellerNotFoundException.class, () -> sellerService.saveSeller(seller));
    }

    @Test
    void saveSellerCallFindAllTest() throws SellerNotFoundException {
        Mockito.doReturn(null)
                .when(sellerRepository)
                .findSellerByName(seller.getName());
        sellerService.saveSeller(seller);
        Mockito.verify(sellerRepository, Mockito.times(1)).findAll();
    }

    @Test
    void saveSellerTest() throws SellerNotFoundException {
        Mockito.doReturn(null)
                .when(sellerRepository)
                .findSellerByName(seller.getName());
        Mockito.doReturn(new ArrayList<>())
                .when(sellerRepository)
                .findAll();
        sellerService.saveSeller(seller);
        Mockito.verify(sellerRepository, Mockito.times(1)).save(seller);
    }


    @Test
    void update() {
    }

    @Test
    void findSellerByActiveStatus() throws SellerNotFoundException {
        Mockito.doReturn(seller)
                .when(sellerRepository)
                .findSellerByActiveStatus();
        sellerService.findSellerByActiveStatus();
        Mockito.verify(sellerRepository, Mockito.times(1)).findSellerByActiveStatus();
    }

    @Test
    void setActiveSeller() {
    }
}