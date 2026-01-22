package com.example.controllers;

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

import com.example.model.Orders;
import com.example.services.OrdersService;

@RestController
@RequestMapping("/api/order")
public class OrderController 
{
	
	@Autowired
	private final OrdersService orderService=null;
	
	@PostMapping("/placeOrder")
	public ResponseEntity<Orders> placeOrder(@RequestBody Orders orders)
	{
		Orders order = orderService.addOrder(orders);
		return ResponseEntity.ok(order);
	}
	
	@GetMapping("/history")
	public ResponseEntity<Optional<List<Orders>>> getOrderHistory(@PathVariable int user_id)
	{
		Optional<List<Orders>> orders = orderService.getOrderById(user_id);
		return ResponseEntity.ok(orders);
	}
	
}
