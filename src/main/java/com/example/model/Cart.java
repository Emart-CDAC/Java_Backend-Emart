package com.example.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="Cart")
public class Cart {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Cart_Id")
	private int Cart_Id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "User_Id")  
	private Customer customer;
	

	@Column(name="Total_Amount", precision=10,scale=2)
	private BigDecimal Total_Amount;
	
	
	public int getCartId() {
		return Cart_Id;
	}
	
	public void setCartId(int cartId) {
		this.Cart_Id=cartId;
	}
	
	public Customer getCustomer() {
		return customer;
	}
	
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	
	public BigDecimal getTotalAmount() {
		return Total_Amount;
	}
	
	public void setTotalAmount(BigDecimal totalAmount) {
		this.Total_Amount=totalAmount;
	}
}
