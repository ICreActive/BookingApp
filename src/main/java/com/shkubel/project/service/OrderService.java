package com.shkubel.project.service;

import com.shkubel.project.exception.OrderNotFoundException;
import com.shkubel.project.models.entity.OrderUser;

import java.util.List;

public interface OrderService {

    List<OrderUser> allOrders();

    OrderUser findOrderById(Long orderId);

    boolean deleteOrderById(Long orderId);

    List<OrderUser> findOrderUsersByUserId(Long userId);

    OrderUser create(OrderUser order);

    List <OrderUser> findOrderUserByActiveStatus();

    void updateOrder(OrderUser order) throws OrderNotFoundException;
}
