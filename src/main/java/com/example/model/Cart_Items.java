package com.example.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class Cart_Items {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int Cart_Item_Id;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Cart_Id", nullable = false)
	private  int cart;
	
	@ManyToOne
	@JoinColumn(name="Product_Id")  
	private int product;
	
	private int Quantity;

	@Column(name="Subtotal", precision = 10, scale = 2)
	private BigDecimal Subtotal;
	
	@Column(name="Total_Price", precision = 10, scale = 2)
	private BigDecimal Total_Price;
	
	
	
	

	public int getCart_Item_Id() {
		return Cart_Item_Id;
	}

	public void setCart_Item_Id(int Cart_Item_Id) {
		this.Cart_Item_Id = Cart_Item_Id;
	}

	public int getCart() {
		return cart;
	}

	public void setCart(int cart) {
		this.cart = cart;
	}

	public int getProduct() {
		return product;
	}

	public void setProduct(int product) {
		this.product = product;
	}

	public int getQuantity() {
		return Quantity;
	}

	public void setQuantity(int Quantity) {
		this.Quantity = Quantity;
	}

	public BigDecimal getSubtotal() {
		return Subtotal;
	}

	public void setSubtotal(BigDecimal Subtotal) {
		this.Subtotal = Subtotal;
	}

	public BigDecimal getTotal_Price() {
		return Total_Price;
	}

	public void setTotal_Price(BigDecimal Total_Price) {
		this.Total_Price = Total_Price;
	}

}























