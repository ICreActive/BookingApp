package com.shkubel.project.dao;

import com.shkubel.project.models.entity.OrderUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderUser,Long> {

    List<OrderUser> findOrderUsersByUserId(Long userId);

    OrderUser findOrderUserById (Long orderId);

    @Query("select u from OrderUser u where u.isActive=true")
    List <OrderUser> findOrderUsersByActiveStatus ();

}
