package com.example.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Customer;
import com.example.model.Orders;

public interface OrderRepository extends JpaRepository<Orders, Integer> {
    Optional<Orders> findLatestOrderByUserId(int userId);
}

