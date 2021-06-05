package com.shkubel.project.repo;

import com.shkubel.project.models.OrderUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderUser,Long> {

    List<OrderUser> findOrderUserByUser_Id(long id);

}
