package com.example.model;

import jakarta.persistence.*;

@Entity
@Table(name = "product")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	private int id;

	@Column(name = "product_name", nullable = false, length = 500)
	private String name;

	@Column(name = "product_image_url", length = 1000)
	private String imageUrl;

	@Column(name = "normal_price", nullable = false)
	private java.math.BigDecimal normalPrice;

	@Column(name = "ecard_price")
	private java.math.BigDecimal ecardPrice;

	@Column(name = "discount_percent", precision = 5, scale = 2)
	private java.math.BigDecimal discountPercent = java.math.BigDecimal.ZERO;

	@Column(name = "available_quantity")
	private int availableQuantity;

	@Column(name = "description", length = 5000)
	private String description;

	// FK → SubCategory
	@ManyToOne
	@JoinColumn(name = "subcategory_id")
	private SubCategory subCategory;

	// FK → Store (kept simple as ID)
	@Column(name = "store_id")
	private int storeId;

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

	public SubCategory getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(SubCategory subCategory) {
		this.subCategory = subCategory;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public Product() {
	}

	// Helper method to calculate discounted price
	public java.math.BigDecimal getEffectivePrice() {
		if (discountPercent != null && discountPercent.compareTo(java.math.BigDecimal.ZERO) > 0) {
			return normalPrice.subtract(
					normalPrice.multiply(discountPercent).divide(java.math.BigDecimal.valueOf(100)));
		}
		return normalPrice;
	}
}
