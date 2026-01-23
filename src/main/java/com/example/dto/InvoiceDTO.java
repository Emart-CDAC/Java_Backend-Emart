package com.example.dto;

import java.util.List;

public class InvoiceDTO 
{
	private int invoiceId;
    private List<CartItemDTO> items;
    private double totalAmount;
    private int earnedEPoints;
    private int redeemedEPoints;
	public int getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(int invoiceId) {
		this.invoiceId = invoiceId;
	}
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
	public int getEarnedEPoints() {
		return earnedEPoints;
	}
	public void setEarnedEPoints(int earnedEPoints) {
		this.earnedEPoints = earnedEPoints;
	}
	public int getRedeemedEPoints() {
		return redeemedEPoints;
	}
	public void setRedeemedEPoints(int redeemedEPoints) {
		this.redeemedEPoints = redeemedEPoints;
	}
    

}
