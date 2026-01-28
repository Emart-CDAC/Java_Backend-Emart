package com.example.dto;

public class LoyaltyProductDTO {
	private int productId;
	private String productName;
	private int redemptionCount;

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getRedemptionCount() {
		return redemptionCount;
	}

	public void setRedemptionCount(int redemptionCount) {
		this.redemptionCount = redemptionCount;
	}
}
