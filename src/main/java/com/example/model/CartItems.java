package com.example.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "Cart_Items")
public class CartItems {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Cart_item_id")
	private int cartItemId;

	@ManyToOne
	@JoinColumn(name = "Cart_Id")
	private Cart cart;

	@ManyToOne
	@JoinColumn(name = "Product_Id")
	private Product product;

	@Column(name = "Quantity")
	private int quantity;

	@Column(name = "Subtotal")
	private double subtotal;

	@Column(name = "Total_Price")
	private double totalPrice;

	@Column(name = "Purchase_Type")
	private String purchaseType; // "NORMAL", "PARTIAL_EP", "FULL_EP"

	@Column(name = "Epoints_Used")
	private Integer epointsUsed;

	public int getCartItemId() {
		return cartItemId;
	}

	public void setCartItemId(int cartItemId) {
		this.cartItemId = cartItemId;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getPurchaseType() {
		return purchaseType;
	}

	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}

	public Integer getEpointsUsed() {
		return epointsUsed;
	}

	public void setEpointsUsed(Integer epointsUsed) {
		this.epointsUsed = epointsUsed;
	}

}
