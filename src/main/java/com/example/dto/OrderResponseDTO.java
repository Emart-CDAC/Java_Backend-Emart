package com.example.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderResponseDTO {

	private int orderId;
	private LocalDateTime orderDate;
	private String status;
	private String paymentStatus;
	private String paymentMethod;
	private String deliveryType;
	private BigDecimal totalAmount;
	private int epointsUsed;
	private int epointsEarned;

	// Customer info (flattened)
	private int customerId;
	private String customerName;
	private String customerEmail;

	// Address info (flattened)
	private Long addressId;
	private String addressLine;
	private String city;
	private String state;
	private String pincode;

	// Store info (if store pickup)
	private Long storeId;
	private String storeName;
	private String storeCity;

	private java.util.List<OrderItemDTO> items;

	public java.util.List<OrderItemDTO> getItems() {
		return items;
	}

	public void setItems(java.util.List<OrderItemDTO> items) {
		this.items = items;
	}

	// Getters and Setters
	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public int getEpointsUsed() {
		return epointsUsed;
	}

	public void setEpointsUsed(int epointsUsed) {
		this.epointsUsed = epointsUsed;
	}

	public int getEpointsEarned() {
		return epointsEarned;
	}

	public void setEpointsEarned(int epointsEarned) {
		this.epointsEarned = epointsEarned;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public Long getAddressId() {
		return addressId;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}

	public String getAddressLine() {
		return addressLine;
	}

	public void setAddressLine(String addressLine) {
		this.addressLine = addressLine;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getStoreCity() {
		return storeCity;
	}

	public void setStoreCity(String storeCity) {
		this.storeCity = storeCity;
	}
}
