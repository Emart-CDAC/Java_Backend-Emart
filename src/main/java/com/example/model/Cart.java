package com.example.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "Cart")
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Cart_Id")
	private int cartId;

	@ManyToOne
	@JoinColumn(name = "User_Id")
	private Customer customer;

	@Column(name = "Total_Amount")
	private double totalAmount;

	@Column(name = "Total_MRP")
	private double totalMrp;

	@Column(name = "Epoint_Discount")
	private double epointDiscount;

	@Column(name = "Coupon_Discount")
	private Double couponDiscount;

	@Column(name = "Platform_Fee")
	private double platformFee;

	@Column(name = "Final_Payable_Amount")
	private double finalPayableAmount;

	@Column(name = "Used_Epoints")
	private int usedEpoints;

	@Column(name = "Earned_Epoints")
	private int earnedEpoints;

	public int getCartId() {
		return cartId;
	}

	public void setCartId(int cartId) {
		this.cartId = cartId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public double getTotalMrp() {
		return totalMrp;
	}

	public void setTotalMrp(double totalMrp) {
		this.totalMrp = totalMrp;
	}

	public double getEpointDiscount() {
		return epointDiscount;
	}

	public void setEpointDiscount(double epointDiscount) {
		this.epointDiscount = epointDiscount;
	}

	public Double getCouponDiscount() {
		return couponDiscount;
	}

	public void setCouponDiscount(Double couponDiscount) {
		this.couponDiscount = couponDiscount;
	}

	public double getPlatformFee() {
		return platformFee;
	}

	public void setPlatformFee(double platformFee) {
		this.platformFee = platformFee;
	}

	public double getFinalPayableAmount() {
		return finalPayableAmount;
	}

	public void setFinalPayableAmount(double finalPayableAmount) {
		this.finalPayableAmount = finalPayableAmount;
	}

	public int getUsedEpoints() {
		return usedEpoints;
	}

	public void setUsedEpoints(int usedEpoints) {
		this.usedEpoints = usedEpoints;
	}

	public int getEarnedEpoints() {
		return earnedEpoints;
	}

	public void setEarnedEpoints(int earnedEpoints) {
		this.earnedEpoints = earnedEpoints;
	}
}
