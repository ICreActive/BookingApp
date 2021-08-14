package com.shkubel.project.service.impl;

import com.shkubel.project.exception.OrderNotFoundException;
import com.shkubel.project.log.InjectLogger;
import com.shkubel.project.models.entity.OrderUser;
import com.shkubel.project.dao.OrderRepository;
import com.shkubel.project.service.OrderService;
import com.shkubel.project.util.DateTimeParser;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @InjectLogger
    private static Logger LOGGER;

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<OrderUser> allOrders() {
        return orderRepository.findAll();
    }

    @Override
    public OrderUser findOrderById(Long orderId) {
        Optional<OrderUser> orderInDB = orderRepository.findById(orderId);
        return orderInDB.orElse(new OrderUser());
    }

    @Override
    public boolean deleteOrderById(Long orderId) {
        if (orderRepository.findById(orderId).isPresent()) {
            OrderUser order = orderRepository.findOrderUserById(orderId);
            order.setActive(false);
            orderRepository.save(order);
            LOGGER.info("Order with id:{} has been disabled successfully", orderId);
            return true;
        }
        LOGGER.error("Order with id:{} not found in database", orderId);
        return false;
    }


    @Override
    public List<OrderUser> findOrderUsersByUserId(Long userId) {
        return orderRepository.findOrderUsersByUserId(userId);
    }

    @Override
    public OrderUser create(OrderUser order) {
        order.setActive(true);
        order.setCreatingDate(DateTimeParser.nowToString());
        LOGGER.info("Order has been created by user with id:{}", order.getUser().getId());
        return orderRepository.save(order);
    }

    public List <OrderUser> findOrderUserByActiveStatus() {
        return orderRepository.findOrderUsersByActiveStatus();
    }

    @Override
    public void updateOrder (OrderUser order) throws OrderNotFoundException {
        OrderUser orderInDb;
        if (orderRepository.findById(order.getId()).isPresent()) {
            orderInDb= orderRepository.findOrderUserById(order.getId());
        orderInDb.setInvoice(order.getInvoice());
        order.setUpdatingDate(DateTimeParser.nowToString());
        orderRepository.save(orderInDb);
        } else {
            LOGGER.error("Order with id:{} not found in database", order.getId());
            throw new OrderNotFoundException("Order not found in DB");
        }
    }

}
