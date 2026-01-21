package com.example.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Orders")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Order_id")
    private int orderId;

    @ManyToOne
    @JoinColumn(name = "User_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "Cart_id")
    private Cart cart;

    @Column(name = "Order_Date")
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status")
    private OrderStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "Payment_Status")
    private PaymentStatus paymentStatus;

    @Column(name = "Total_Amount")
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    private DeliveryType deliveryType;

    @Column(name = "epoints_used")
    private int epointsUsed;

    @ManyToOne
    @JoinColumn(name = "Address_id")
    private Address address;

    @Column(name = "epoints_earned")
    private int epointsEarned;

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public DeliveryType getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(DeliveryType deliveryType) {
		this.deliveryType = deliveryType;
	}

	public int getEpointsUsed() {
		return epointsUsed;
	}

	public void setEpointsUsed(int epointsUsed) {
		this.epointsUsed = epointsUsed;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public int getEpointsEarned() {
		return epointsEarned;
	}

	public void setEpointsEarned(int epointsEarned) {
		this.epointsEarned = epointsEarned;
	}

    
}
