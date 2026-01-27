package com.example.dto;

import java.util.List;

public class CartResponseDTO {
	private List<CartItemDTO> items;
	private double totalAmount;
	private int totalEPointsUsed;

	// Pricing breakdown
	private double totalMrp;
	private double epointDiscount;
	private double couponDiscount;
	private double platformFee;
	private double finalPayableAmount;
	private int usedEpoints;
	private int earnedEpoints;
	private int availableEpoints; // User's total balance

	public List<CartItemDTO> getItems() {
		return items;
	}

	public void setItems(List<CartItemDTO> items) {
		this.items = items;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public int getTotalEPointsUsed() {
		return totalEPointsUsed;
	}

	public void setTotalEPointsUsed(int totalEPointsUsed) {
		this.totalEPointsUsed = totalEPointsUsed;
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

	public double getCouponDiscount() {
		return couponDiscount;
	}

	public void setCouponDiscount(double couponDiscount) {
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

	public int getAvailableEpoints() {
		return availableEpoints;
	}

	public void setAvailableEpoints(int availableEpoints) {
		this.availableEpoints = availableEpoints;
	}

}
