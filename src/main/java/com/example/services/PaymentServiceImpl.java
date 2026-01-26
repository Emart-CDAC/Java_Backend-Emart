package com.example.services;

import java.time.LocalDateTime;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.model.Orders;
import com.example.model.Payment;
import com.example.model.PaymentMethod;
import com.example.model.PaymentStatus;
import com.example.repository.OrdersRepository;
import com.example.repository.PaymentRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;


@Service
public class PaymentServiceImpl implements PaymentService {
	
	@Value("${razorpay.key.id}")
	private String keyId;

	@Value("${razorpay.key.secret}")
	private String keySecret;

	@Autowired
	private PaymentRepository paymentRepository;
	
	@Autowired
	private OrdersRepository ordersRepository; 

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

	
	//--------------------------------razorPay---------------------------------------
	
	@Override
	public String createRazorpayOrder(int orderId) throws RazorpayException {


		 Orders order = ordersRepository.findById(orderId)
		            .orElseThrow(() -> new RuntimeException("Order not found"));

		    double amount = order.getTotalAmount().doubleValue(); // ✅ use actual order amount
		    
	    RazorpayClient razorpayClient = new RazorpayClient(keyId, keySecret);

	    JSONObject orderRequest = new JSONObject();
	    orderRequest.put("amount", (int)(amount * 100)); // rupees->paise
	    orderRequest.put("currency", "INR");
	    orderRequest.put("receipt", "receipt_" + orderId);

	    Order razorOrder = razorpayClient.orders.create(orderRequest);

	    // 2) Save Payment record as PENDING
	    Payment payment = new Payment();
	    payment.setAmount(amount);
	    payment.setPaymentDate(LocalDateTime.now());
	    payment.setStatus(PaymentStatus.PENDING);
	    payment.setPaymentMethod(PaymentMethod.RAZORPAY);

	    payment.setRazorpayOrderId(razorOrder.get("id"));
	    
	    // ✅ THIS LINE IS IMPORTANT (attach order)
	    payment.setOrder(order);
	    
	    paymentRepository.save(payment);

	    return razorOrder.toString();
	}

	
	
	@Override
	public Payment verifyRazorpayPayment(int orderId, Payment payment) throws Exception {

	    Payment dbPayment = paymentRepository.findByOrder_OrderId(orderId);

	    if (dbPayment == null) {
	        throw new RuntimeException("Payment record not found for order");
	    }

	    String data = payment.getRazorpayOrderId() + "|" + payment.getRazorpayPaymentId();
	    String generatedSignature = hmacSha256(data, keySecret);

	    if (generatedSignature.equals(payment.getRazorpaySignature())) {

	        dbPayment.setStatus(PaymentStatus.PAID);
	        dbPayment.setTransactionId(payment.getRazorpayPaymentId());

	        dbPayment.setRazorpayPaymentId(payment.getRazorpayPaymentId());
	        dbPayment.setRazorpaySignature(payment.getRazorpaySignature());

	        return paymentRepository.save(dbPayment);

	    } else {
	        dbPayment.setStatus(PaymentStatus.FAILED);
	        return paymentRepository.save(dbPayment);
	    }
	}
	
	@Override
	public Payment createCashOnDeliveryPayment(int orderId) {

	    Orders order = ordersRepository.findById(orderId)
	            .orElseThrow(() -> new RuntimeException("Order not found"));

	    Payment payment = new Payment();
	    payment.setOrder(order);
	    payment.setAmount(order.getTotalAmount().doubleValue());
	    payment.setPaymentDate(LocalDateTime.now());
	    payment.setPaymentMethod(PaymentMethod.CASH);
	    payment.setStatus(PaymentStatus.PENDING);  // ✅ COD pending

	    return paymentRepository.save(payment);
	}

	private String hmacSha256(String data, String secret) throws Exception {
	    Mac sha256Hmac = Mac.getInstance("HmacSHA256");
	    SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
	    sha256Hmac.init(secretKey);
	    return Hex.encodeHexString(sha256Hmac.doFinal(data.getBytes()));
	}



	
	
}
