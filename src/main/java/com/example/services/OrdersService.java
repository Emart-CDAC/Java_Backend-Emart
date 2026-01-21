package com.example.services;

import java.util.List;
import java.util.Optional;

import com.example.model.Orders;

public interface OrdersService 
{
	Orders addOrder(Orders orders);
	Optional<List<Orders>> getOrderById(int id);
}
