package com.example.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.example.services.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/checkout")
public class CheckoutController 
{
	@Autowired
	CheckoutService checkoutservice;
	
	@PostMapping("/delivery")
	public ResponseEntity<?> selectDeliveryOption(@RequestParam int userId ,@RequestParam String deliveryType )
	{
		return ResponseEntity.ok(checkoutservice.selectDeliveryOption(userId,deliveryType));
	}
	
	@PostMapping("/place-order")
	public ResponseEntity<?> placeOrder(@RequestParam int userId)
	{
		return ResponseEntity.ok(checkoutservice.placeOrder(userId));
	}
	

}
