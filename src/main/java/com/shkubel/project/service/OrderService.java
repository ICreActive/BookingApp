package com.shkubel.project.service;

import com.shkubel.project.models.OrderUser;
import com.shkubel.project.repo.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private OrderRepository orderRepository;

    public List<OrderUser> allOrders() {
        return orderRepository.findAll();
    }

    public OrderUser findOrderById (long orderId) {
        Optional <OrderUser> orderInDB = orderRepository.findById(orderId);
        return orderInDB.orElse(new OrderUser());
    }

    public boolean deleteOrderById (long orderId) {
        if (orderRepository.findById(orderId).isPresent()) {
            orderRepository.deleteById(orderId);
            return true;
        }
        return false;
    }



    public List<OrderUser> findOrderByUserId(long userId) {
        return em.createQuery("SELECT u FROM OrderUser u WHERE u.user.id = :userId", OrderUser.class)
                .setParameter("userId", userId).getResultList();
    }
}
