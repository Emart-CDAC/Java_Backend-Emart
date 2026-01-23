package com.example.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.example.model.Customer;
import com.example.model.Orders;

public interface OrdersService 
{
	Orders placeOrder(Customer customer, BigDecimal totalAmount, int useEpoints);
}
