package com.example.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Payment;
import com.example.model.PaymentStatus;
import com.example.services.PaymentService;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

	@Autowired
	private PaymentService paymentService;
	
	@PostMapping("/process")
	public Payment processPayment(@RequestBody Payment payment) {
		return paymentService.processPayment(payment);
	}
	
	@GetMapping("/status/{orderId}")
	public PaymentStatus getStatus(@PathVariable int orderId) {
		return paymentService.getPaymentStatus(orderId);
	}
	
}
