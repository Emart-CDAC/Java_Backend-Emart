package com.example.model;

import jakarta.persistence.*;

@Entity
@Table(name = "product")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	private int id;

	@Column(name = "product_name", nullable = false, length = 100)
	private String name;

	@Column(name = "product_image_url", length = 255)
	private String imageUrl;

	@Column(name = "normal_price", nullable = false)
	private double normalPrice;

	@Column(name = "ecard_price")
	private Double ecardPrice;

	@Column(name = "available_quantity")
	private int availableQuantity;

	@Column(name = "description")
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

	public double getNormalPrice() {
		return normalPrice;
	}

	public void setNormalPrice(double normalPrice) {
		this.normalPrice = normalPrice;
	}

	public Double getEcardPrice() {
		return ecardPrice;
	}

	public void setEcardPrice(Double ecardPrice) {
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

}
