package com.example.dto;

public class ProductRequestDTO {
	private String name;
	private String imageUrl;
	private java.math.BigDecimal normalPrice;
	private java.math.BigDecimal ecardPrice;
	private int availableQuantity;
	private String description;
	private int subCategoryId;
	private int storeId;

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

	public int getSubCategoryId() {
		return subCategoryId;
	}

	public void setSubCategoryId(int subCategoryId) {
		this.subCategoryId = subCategoryId;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

}
