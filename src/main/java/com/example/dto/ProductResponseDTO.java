package com.example.dto;

public class ProductResponseDTO {
	private int id;
	private String name;
	private String imageUrl;
	private java.math.BigDecimal normalPrice;
	private java.math.BigDecimal ecardPrice;
	private java.math.BigDecimal discountPercent;
	private int availableQuantity;
	private String description;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public java.math.BigDecimal getNormalPrice() {
		return normalPrice;
	}

	public void setNormalPrice(java.math.BigDecimal normalPrice) {
		this.normalPrice = normalPrice;
	}

	public java.math.BigDecimal getEcardPrice() {
		return ecardPrice;
	}

	public void setEcardPrice(java.math.BigDecimal ecardPrice) {
		this.ecardPrice = ecardPrice;
	}

	public java.math.BigDecimal getDiscountPercent() {
		return discountPercent;
	}

	public void setDiscountPercent(java.math.BigDecimal discountPercent) {
		this.discountPercent = discountPercent;
	}

	public int getAvailableQuantity() {
		return availableQuantity;
	}

	public void setAvailableQuantity(int availableQuantity) {
		this.availableQuantity = availableQuantity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
