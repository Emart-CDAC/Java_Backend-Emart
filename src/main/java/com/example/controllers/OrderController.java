package com.example.controllers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Customer;
import com.example.model.Orders;
import com.example.services.OrdersService;

@RestController
@RequestMapping("/api/order")
public class OrderController 
{
	
	@Autowired
	OrdersService orderService;
	
	@PostMapping("/placeOrder")
	public ResponseEntity<String> placeOrder(Customer customer, BigDecimal totalAmount, int useEpoints) {
	    orderService.placeOrder(customer, totalAmount, useEpoints);
	    return ResponseEntity.ok("Order placed successfully");
	}

}
