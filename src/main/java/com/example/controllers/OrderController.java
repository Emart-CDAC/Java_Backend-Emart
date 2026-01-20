package com.example.controllers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Orders;
import com.example.model.Product;
import com.example.services.OrderService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/order")
public class OrderController 
{
	
	@Autowired
	private final OrderService orderService;
	
	@PostMapping("/placeOrder")
	public ResponseEntity<Orders> placeOrder(@RequestBody Orders orders)
	{
		Orders order = orderService.place(orders);
		return ResponseEntity.ok(order);
	}
	
	@GetMapping("/history")
	public ResponseEntity<List<Orders>> getOrderHistory(@PathVariable int user_id)
	{
		List<Orders> orders = orderService.getById(user_id);
		return ResponseEntity.ok(orders);
	}
	
}
