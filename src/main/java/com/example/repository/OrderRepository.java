package com.example.repository;

<<<<<<< HEAD
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.model.Orders;


public interface OrderRepository extends JpaRepository<Orders, Integer>
{

}
=======
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Customer;
import com.example.model.Orders;

public interface OrderRepository extends JpaRepository<Orders, Integer> {
    Optional<Orders> findLatestOrderByUserId(int userId);
}

>>>>>>> b810669ff15012dee2f3f34e7eb1cbb5fd12af50
