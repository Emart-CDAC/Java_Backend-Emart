package com.example.model;

import jakarta.persistence.*;

@Entity
public class Order_items {
	private int order_item_id;
	private int order_id;
	private int product_id;
	private int quantity;
	private double price;
	private double subtotal;

	public Order_items() {
	}

	public Order_items(int orderItemId, int orderId, int productId, int quantity, 
			double price, double subtotal) {

		this.order_item_id = orderItemId;
		this.order_id = orderId;
		this.product_id = productId;
		this.quantity = quantity;
		this.price = price;
		this.subtotal = subtotal;
	}

	public int getOrderItemId() {
		return order_item_id;
	}

	public void setOrderItemId(int orderItemId) {
		this.order_item_id = orderItemId;
	}

	public int getOrderId() {
		return order_id;
	}

	public void setOrderId(int orderId) {
		this.order_id = orderId;
	}

	public int getProductId() {
		return product_id;
	}

	public void setProductId(int productId) {
		this.product_id = productId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}
}
