package com.shkubel.project.service;

import com.shkubel.project.models.entity.OrderUser;

import java.util.List;

public interface OrderService {

    List<OrderUser> allOrders();
    OrderUser findOrderById (long orderId);
    boolean deleteOrderById (long orderId);
    int bookingPeriod (OrderUser orderUser);
    List<OrderUser> findOrderByUserId(long userId);

}
