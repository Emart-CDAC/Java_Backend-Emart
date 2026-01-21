package com.example.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Payment;
import com.example.model.PaymentMethod;
import com.example.model.PaymentStatus;
import com.example.services.PaymentService;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/process") //Process and save payment
    public ResponseEntity<Payment> processPayment(@RequestBody Payment payment) {
        return ResponseEntity.ok(paymentService.processPayment(payment));
    }

    @GetMapping("/status/{orderId}") //Get payment status
    public ResponseEntity<PaymentStatus> getStatus(@PathVariable int orderId) {
        return ResponseEntity.ok(paymentService.getPaymentStatus(orderId));
    }

    @GetMapping("/method/{orderId}") //Get payment method
    public ResponseEntity<PaymentMethod> getPaymentMethod(@PathVariable int orderId) {
        return ResponseEntity.ok(paymentService.getPaymentMethod(orderId));
    }

    @GetMapping("/order/{orderId}") //Get full payment details
    public ResponseEntity<Payment> getPaymentByOrderId(@PathVariable int orderId) {
        return ResponseEntity.ok(paymentService.getPaymentByOrderId(orderId));
    }
}
