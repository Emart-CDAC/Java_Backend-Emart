package com.example.dto;

public class ProductOfferInventoryDTO {
	private String product_Name;
	private String discount_Offer;
	private int quantity;

	public String getProduct_Name() {
		return product_Name;
	}

	public void setProduct_Name(String product_Name) {
		this.product_Name = product_Name;
	}

	public String getDiscount_Offer() {
		return discount_Offer;
	}

	public void setDiscount_Offer(String discount_Offer) {
		this.discount_Offer = discount_Offer;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
