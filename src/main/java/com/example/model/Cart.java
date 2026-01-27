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
	@com.fasterxml.jackson.annotation.JsonIgnore
	private Customer customer;

	@Column(name = "Total_Amount")
	private BigDecimal totalAmount;

	@Column(name = "Total_MRP")
	private BigDecimal totalMrp;

	@Column(name = "Epoint_Discount")
	private BigDecimal epointDiscount;

	@Column(name = "Coupon_Discount")
	private BigDecimal couponDiscount;

	@Column(name = "Platform_Fee")
	private BigDecimal platformFee;

	@Column(name = "Final_Payable_Amount")
	private BigDecimal finalPayableAmount;

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

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getTotalMrp() {
		return totalMrp;
	}

	public void setTotalMrp(BigDecimal totalMrp) {
		this.totalMrp = totalMrp;
	}

	public BigDecimal getEpointDiscount() {
		return epointDiscount;
	}

	public void setEpointDiscount(BigDecimal epointDiscount) {
		this.epointDiscount = epointDiscount;
	}

	public BigDecimal getCouponDiscount() {
		return couponDiscount;
	}

	public void setCouponDiscount(BigDecimal couponDiscount) {
		this.couponDiscount = couponDiscount;
	}

	public BigDecimal getPlatformFee() {
		return platformFee;
	}

	public void setPlatformFee(BigDecimal platformFee) {
		this.platformFee = platformFee;
	}

	public BigDecimal getFinalPayableAmount() {
		return finalPayableAmount;
	}

	public void setFinalPayableAmount(BigDecimal finalPayableAmount) {
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
