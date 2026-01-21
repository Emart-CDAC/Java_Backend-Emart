package com.example.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Payment;
import com.example.model.PaymentStatus;
import com.example.repository.PaymentRepository;
import com.example.services.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Payment processPayment(Payment payment) {
        payment.setPaymentDate(LocalDateTime.now());

        if (payment.getStatus() == null) {
            payment.setStatus(PaymentStatus.PENDING);
        }

        return paymentRepository.save(payment);
    }

    @Override
    public PaymentStatus getPaymentStatus(int orderId) {

    	Payment payment = paymentRepository.findByOrder_OrderId(orderId);

    	if (payment == null) {
    	    throw new RuntimeException("Payment not found for order");
    	}

    	return payment.getStatus();

    }
}
