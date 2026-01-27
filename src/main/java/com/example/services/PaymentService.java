package com.example.services;

import com.example.model.Payment;
import com.example.model.PaymentMethod;
import com.example.model.PaymentStatus;
import com.razorpay.RazorpayException;

public interface PaymentService {

	Payment processPayment(Payment payment);

	PaymentStatus getPaymentStatus(int orderId);

	PaymentMethod getPaymentMethod(int orderId);

	// Payment getPaymentByOrderId(int orderId);

	String createRazorpayOrder(int orderId) throws RazorpayException;

	String createRazorpayOrder(double amount) throws RazorpayException;

	Payment verifyRazorpayPayment(int orderId, Payment payment) throws Exception;

	Payment createCashOnDeliveryPayment(int orderId);

}
