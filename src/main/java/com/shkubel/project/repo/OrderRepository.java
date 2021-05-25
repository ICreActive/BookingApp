package com.shkubel.project.repo;

import com.shkubel.project.models.OrderUser;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<OrderUser,Long> {

    List<OrderUser> findOrderUserByUser_Id(long id);

}
