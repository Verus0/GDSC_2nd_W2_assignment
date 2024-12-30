package com.example.gdsc_2nd_w1.Order;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<r_Order, Integer> {
    List<r_Order> findByUser_Id(Integer id);
}
