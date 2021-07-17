package com.shkubel.project.service;

import com.shkubel.project.models.entity.OrderUser;

import java.util.List;

public interface OrderService {

    List<OrderUser> allOrders();

    OrderUser findOrderById(Long orderId);

    boolean deleteOrderById(Long orderId);

    int bookingPeriod(OrderUser order);

    List<OrderUser> findOrderUsersByUserId(Long userId);

    OrderUser create(OrderUser order);

}
