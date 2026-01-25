package com.example.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Sub_Category")
public class SubCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SubCategory_id")
	private int subCategoryId;

	@ManyToOne
	@JoinColumn(name = "Category_id", nullable = false)
	private Category category;

	@Column(name = "Brand")
	private String brand;

	@Column(name = "Sponsors")
	private boolean sponsors;

	public int getSubCategoryId() {
		return subCategoryId;
	}

	public void setSubCategoryId(int subCategoryId) {
		this.subCategoryId = subCategoryId;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public boolean isSponsors() {
		return sponsors;
	}

	public void setSponsors(boolean sponsors) {
		this.sponsors = sponsors;
	}
}
