package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.model.Orders;

import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders, Integer> {
    List<Orders> findByCustomerUserId(int userId);
}
