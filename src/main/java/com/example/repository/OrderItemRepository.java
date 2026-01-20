package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.OrderItems;
import com.example.model.Orders;

public interface OrderItemRepository extends JpaRepository<OrderItems, Integer> {

    List<OrderItems> findByOrder(Orders order);


    List<OrderItems> findByOrder_OrderId(int orderId);
}
