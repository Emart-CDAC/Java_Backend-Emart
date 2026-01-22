package com.example.dto;

public class ProductDTO 
{
	private int productId;
    private String productName;
    private String brand;
    private double price;
    private double emCardPrice;
    private int ePointsRequired;
    private int categoryId;
    private String imagePath;
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
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getEmCardPrice() {
		return emCardPrice;
	}
	public void setEmCardPrice(double emCardPrice) {
		this.emCardPrice = emCardPrice;
	}
	public int getePointsRequired() {
		return ePointsRequired;
	}
	public void setePointsRequired(int ePointsRequired) {
		this.ePointsRequired = ePointsRequired;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
    
    

}
