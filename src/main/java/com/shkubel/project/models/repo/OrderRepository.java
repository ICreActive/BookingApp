package com.shkubel.project.models.repo;

import com.shkubel.project.models.entity.OrderUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderUser,Long> {

    List<OrderUser> findOrderUsersByUserId(Long userId);

}
