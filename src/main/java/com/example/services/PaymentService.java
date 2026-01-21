package com.example.services;

import com.example.model.Payment;
import com.example.model.PaymentStatus;

public interface PaymentService {

    Payment processPayment(Payment payment);

    PaymentStatus getPaymentStatus(int orderId);
}
