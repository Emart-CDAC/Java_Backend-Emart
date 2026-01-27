package com.example.services;

import java.util.List;
import com.example.model.Orders;
import com.example.dto.PlaceOrderRequest;
import com.example.model.OrderStatus;

public interface OrdersService {

	Orders placeOrder(PlaceOrderRequest req);

	Orders updateOrderStatus(int orderId, OrderStatus status);

	Orders getOrderById(int orderId);

	List<Orders> getOrdersByUserId(int userId);
}
