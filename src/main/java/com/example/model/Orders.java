package com.example.Model;

import jakarta.persistence.*;

enum OrderStatus {
	PENDING, 
	CONFIRMED,
	SHIPPED,
	DELIVERED,
	CANCELLED
}

enum PaymentStatus {
	PENDING,
	PAID,
	FAILED
}

enum Delivery {
	STORE,
	HOME_DELIVERY
}

@Entity
public class Orders {
	private int order_id;
	private int User_id;
	private int Cart_id;
	private int order_date;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus status;
	
	@Enumerated(EnumType.STRING)
	private PaymentStatus payment_status;
	
	@Enumerated(EnumType.STRING)
	private Delivery delivery_type;
	
	private double total_amount;
	private int epoints_used;
	private int Address_id;
	private int epoints_earned;

	public Orders() {
	}

//	public Orders(int orderId, int userId, int cartId, int orderDate, String status, String paymentStatus,
//			double totalAmt, String deliveryType, int epointsUsed, int addressId, int epointsEarned) {
//		
//		this.order_id = orderId;
//		this.User_id = userId;
//		this.Cart_id = cartId;
//		this.order_date = orderDate;
//		this.status = status;
//		this.payment_status = paymentStatus;
//		this.total_amount = totalAmt;
//		this.delivery_type = deliveryType;
//		this.epoints_used = epointsUsed;
//		this.Address_id = addressId;
//		this.epoints_earned = epointsEarned;
//	}

	public int getOrderId() {
		return order_id;
	}

	public void setOrderId(int orderId) {
		this.order_id = orderId;
	}

	public int getUserId() {
		return User_id;
	}

	public void setUserId(int userId) {
		this.User_id = userId;
	}

	public int getCartId() {
		return Cart_id;
	}

	public void setCartId(int cartId) {
		this.Cart_id = cartId;
	}

	public int getOrderDate() {
		return order_date;
	}

	public void setOrderDate(int orderdate) {
		this.order_date = orderdate;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public PaymentStatus getPaymentStatus() {
		return payment_status;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.payment_status = paymentStatus;
	}

	public double getTotalAmount() {
		return total_amount;
	}

	public void setTotalAmount(double totalAmount) {
		this.total_amount = totalAmount;
	}

	public Delivery getDeliveryType() {
		return delivery_type;
	}

	public void setDeliveryType(Delivery deliveryType) {
		this.delivery_type = deliveryType;
	}

	public int getEpointsUsed() {
		return epoints_used;
	}

	public void setEpointsUsed(int epointsUsed) {
		this.epoints_used = epointsUsed;
	}

	public int getAddressId() {
		return Address_id;
	}

	public void setAddressId(int addressId) {
		this.Address_id = addressId;
	}

	public int getEpointsEarned() {
		return epoints_earned;
	}

	public void setEpointsEarned(int epointsEarned) {
		this.epoints_earned = epointsEarned;
	}
}
