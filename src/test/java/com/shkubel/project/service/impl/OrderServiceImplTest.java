package com.shkubel.project.service.impl;

import com.shkubel.project.models.entity.KlassAppartament;
import com.shkubel.project.models.entity.OrderUser;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest
class OrderServiceImplTest {

    @Autowired
    private OrderServiceImpl orderService;

    private OrderUser order;


    @BeforeEach
    public void setUp () {
        order = new OrderUser();
        order.setLocalDateStart(LocalDate.of(2021, 7,20));
        order.setLocalDateFinish(LocalDate.of(2021, 7,23));
        order.setKlassOfApartment(KlassAppartament.HIGH);
        order.setNumberOfSeats(2);
        order.setId(1L);
    }

    @Test
    void bookingPeriodDaysTest() {
        Assertions.assertEquals(orderService.bookingPeriod(order), 3);
    }

    @Test
    void deleteOrderById() {
        orderService.deleteOrderById(1L);
        Assertions.assertFalse(orderService.findOrderById(1L).isActive());
    }
}