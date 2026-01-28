package com.example.dto;

import java.util.List;

public class CartResponseDTO {
	private List<CartItemDTO> items;
	private java.math.BigDecimal totalAmount;
	private int totalEPointsUsed;

	// Pricing breakdown
	private java.math.BigDecimal totalMrp;
	private java.math.BigDecimal epointDiscount;
	private java.math.BigDecimal couponDiscount;
	private java.math.BigDecimal platformFee;
	private java.math.BigDecimal finalPayableAmount;
	private int usedEpoints;
	private int earnedEpoints;
	private int availableEpoints; // User's total balance

	public List<CartItemDTO> getItems() {
		return items;
	}

	public void setItems(List<CartItemDTO> items) {
		this.items = items;
	}

	public java.math.BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(java.math.BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public int getTotalEPointsUsed() {
		return totalEPointsUsed;
	}

	public void setTotalEPointsUsed(int totalEPointsUsed) {
		this.totalEPointsUsed = totalEPointsUsed;
	}

	public java.math.BigDecimal getTotalMrp() {
		return totalMrp;
	}

	public void setTotalMrp(java.math.BigDecimal totalMrp) {
		this.totalMrp = totalMrp;
	}

	public java.math.BigDecimal getEpointDiscount() {
		return epointDiscount;
	}

	public void setEpointDiscount(java.math.BigDecimal epointDiscount) {
		this.epointDiscount = epointDiscount;
	}

	public java.math.BigDecimal getCouponDiscount() {
		return couponDiscount;
	}

	public void setCouponDiscount(java.math.BigDecimal couponDiscount) {
		this.couponDiscount = couponDiscount;
	}

	public java.math.BigDecimal getPlatformFee() {
		return platformFee;
	}

	public void setPlatformFee(java.math.BigDecimal platformFee) {
		this.platformFee = platformFee;
	}

	public java.math.BigDecimal getFinalPayableAmount() {
		return finalPayableAmount;
	}

	public void setFinalPayableAmount(java.math.BigDecimal finalPayableAmount) {
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

	public int getAvailableEpoints() {
		return availableEpoints;
	}

	public void setAvailableEpoints(int availableEpoints) {
		this.availableEpoints = availableEpoints;
	}

	private java.math.BigDecimal gstAmount;
	private java.math.BigDecimal offerDiscount;

	public java.math.BigDecimal getGstAmount() {
		return gstAmount;
	}

	public void setGstAmount(java.math.BigDecimal gstAmount) {
		this.gstAmount = gstAmount;
	}

	public java.math.BigDecimal getOfferDiscount() {
		return offerDiscount;
	}

	public void setOfferDiscount(java.math.BigDecimal offerDiscount) {
		this.offerDiscount = offerDiscount;
	}
}
