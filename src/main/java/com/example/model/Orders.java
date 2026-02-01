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
	@com.fasterxml.jackson.annotation.JsonIgnoreProperties({ "address", "emartCard", "authorities", "password" })
	private Customer customer;

	@ManyToOne
	@JoinColumn(name = "Cart_id")
	@com.fasterxml.jackson.annotation.JsonIgnore
	private Cart cart;
	@ManyToOne
	@JoinColumn(name = "store_id")
	private Store store;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@com.fasterxml.jackson.annotation.JsonIgnore
	private java.util.List<OrderItems> orderItems;

	public java.util.List<OrderItems> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(java.util.List<OrderItems> orderItems) {
		this.orderItems = orderItems;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	@Column(name = "Order_Date")
	private LocalDateTime orderDate;

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

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "Payment_Method")
	private PaymentMethod paymentMethod;

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
