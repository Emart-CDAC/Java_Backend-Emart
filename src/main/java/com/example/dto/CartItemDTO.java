package com.example.dto;

public class CartItemDTO {
	private int cartItemId;
	private int productId;
	private String productName;
	private int quantity;
	private java.math.BigDecimal price;
	private java.math.BigDecimal discountedPrice;
	private String purchaseType;
	private int epointsUsed;
	private String imageUrl;

	public int getCartItemId() {
		return cartItemId;
	}

	public void setCartItemId(int cartItemId) {
		this.cartItemId = cartItemId;
	}

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

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public java.math.BigDecimal getPrice() {
		return price;
	}

	public void setPrice(java.math.BigDecimal price) {
		this.price = price;
	}

	public java.math.BigDecimal getDiscountedPrice() {
		return discountedPrice;
	}

	public void setDiscountedPrice(java.math.BigDecimal discountedPrice) {
		this.discountedPrice = discountedPrice;
	}

	public String getPurchaseType() {
		return purchaseType;
	}

	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}

	public int getEpointsUsed() {
		return epointsUsed;
	}

	public void setEpointsUsed(int epointsUsed) {
		this.epointsUsed = epointsUsed;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

}
