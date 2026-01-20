package com.example.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "Product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int productId;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "product_image_url")
    private String productImageUrl;

    @ManyToOne
    @JoinColumn(name = "subcategory_id")
    private SubCategory subCategory;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @Column(name = "normal_price", nullable = false)
    private BigDecimal normalPrice;

    @Column(name = "ecard_price")
    private BigDecimal ecardPrice;

    @Column(name = "available_quantity")
    private int availableQuantity;

    @Column(name = "description")
    private String description;

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

	public String getProductImageUrl() {
		return productImageUrl;
	}

	public void setProductImageUrl(String productImageUrl) {
		this.productImageUrl = productImageUrl;
	}

	public SubCategory getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(SubCategory subCategory) {
		this.subCategory = subCategory;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public BigDecimal getNormalPrice() {
		return normalPrice;
	}

	public void setNormalPrice(BigDecimal normalPrice) {
		this.normalPrice = normalPrice;
	}

	public BigDecimal getEcardPrice() {
		return ecardPrice;
	}

	public void setEcardPrice(BigDecimal ecardPrice) {
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

    
}
