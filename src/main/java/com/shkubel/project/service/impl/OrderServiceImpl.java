package com.shkubel.project.service.impl;

import com.shkubel.project.models.entity.OrderUser;
import com.shkubel.project.models.repo.OrderRepository;
import com.shkubel.project.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<OrderUser> allOrders() {
        return orderRepository.findAll();
    }

    @Override
    public OrderUser findOrderById (Long orderId) {
        Optional <OrderUser> orderInDB = orderRepository.findById(orderId);
        return orderInDB.orElse(new OrderUser());
    }

    @Override
    public boolean deleteOrderById (Long orderId) {
        if (orderRepository.findById(orderId).isPresent()) {
            orderRepository.deleteById(orderId);
            return true;
        }
        return false;
    }
    @Override
    public int bookingPeriod (OrderUser orderUser) {
        Period days = Period.between(orderUser.getLocalDateFinish(), orderUser.getLocalDateStart());
        return Math.abs(days.getDays());
    }

    @Override
    public List<OrderUser> findOrderUsersByUserId(Long userId) {
        return orderRepository.findOrderUsersByUserId(userId);
    }

}
