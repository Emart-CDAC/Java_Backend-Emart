package com.example.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Cart;
import com.example.model.Customer;
import com.example.model.Orders;
import com.example.model.Payment;
import com.example.model.PaymentMethod;
import com.example.model.PaymentStatus;
import com.example.repository.CartRepository;
import com.example.repository.CustomerRepository;
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
		if (payment.getPaymentMethod() == null) {
			payment.setPaymentMethod(PaymentMethod.CASH);
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

	@Override
	public PaymentMethod getPaymentMethod(int orderId) {

		Payment payment = paymentRepository.findByOrder_OrderId(orderId);

		if (payment == null) {
			throw new RuntimeException("Payment mode not found for order");
		}
		return payment.getPaymentMethod();
	}

	@Override
	public Payment getPaymentByOrderId(int orderId) {
	    Payment payment = paymentRepository.findByOrder_OrderId(orderId);

	    if (payment == null) {
	        throw new RuntimeException("Payment not found for order");
	    }
	    return payment;
	}

}
