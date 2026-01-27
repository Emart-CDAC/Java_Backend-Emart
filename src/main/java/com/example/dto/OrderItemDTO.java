package com.example.dto;

import java.math.BigDecimal;

public class OrderItemDTO {
    private String productName;
    private int quantity;
    private double price;
    private double subtotal;
    private String imageUrl; // Optional, if you want to show image

    public OrderItemDTO(String productName, int quantity, double price, double subtotal) {
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.subtotal = subtotal;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
}
