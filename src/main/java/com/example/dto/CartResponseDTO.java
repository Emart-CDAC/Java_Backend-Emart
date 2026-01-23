package com.example.dto;

import java.util.List;

public class CartResponseDTO 
{
	 private List<CartItemDTO> items;
	 private double totalAmount;
	 private int totalEPointsUsed;
	public List<CartItemDTO> getItems() {
		return items;
	}
	public void setItems(List<CartItemDTO> items) {
		this.items = items;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public int getTotalEPointsUsed() {
		return totalEPointsUsed;
	}
	public void setTotalEPointsUsed(int totalEPointsUsed) {
		this.totalEPointsUsed = totalEPointsUsed;
	}
	 

}
