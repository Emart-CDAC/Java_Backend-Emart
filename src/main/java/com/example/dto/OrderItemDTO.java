package com.example.dto;

import java.math.BigDecimal;

public class OrderItemDTO {
    private String productName;
    private int quantity;
    private java.math.BigDecimal price;
    private java.math.BigDecimal subtotal;
    private String imageUrl; // Optional, if you want to show image

    public OrderItemDTO(String productName, int quantity, java.math.BigDecimal price, java.math.BigDecimal subtotal) {
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

    public java.math.BigDecimal getPrice() {
        return price;
    }

    public void setPrice(java.math.BigDecimal price) {
        this.price = price;
    }

    public java.math.BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(java.math.BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
}
