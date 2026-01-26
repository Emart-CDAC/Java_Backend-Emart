package com.example.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.example.model.Orders;
import com.example.model.OrderStatus;

public interface OrdersService {
	Orders placeOrder(int userId, BigDecimal totalAmount, int useEpoints, String deliveryType, String address);

	Orders updateOrderStatus(int orderId, OrderStatus status);

	List<Orders> getOrdersByUserId(int userId);
}
