package com.example.services;

import java.util.List;
import com.example.model.Orders;
import com.example.model.OrderStatus;

public interface OrdersService {

	Orders placeOrder(
			int userId,
			String deliveryType,
			String address);

	Orders updateOrderStatus(int orderId, OrderStatus status);

	List<Orders> getOrdersByUserId(int userId);
}
