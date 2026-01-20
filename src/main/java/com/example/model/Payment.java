package com.example.model;

import java.time.LocalDateTime;
import jakarta.persistence.*;

enum PaymentStatus {
    PENDING,
    PAID,
    FAILED
}

@Entity
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int Payment_id;

	private int Order_id;

	private LocalDateTime Payment_date;

	@Column(name="Amount", precision=10, scale=2)
	private double Amount;

	@Enumerated(EnumType.STRING)
	private PaymentStatus Status;

	public int getPayment_id() {
		return Payment_id;
	}

	public void setPayment_id(int payment_id) {
		Payment_id = payment_id;
	}
	
	public int getOrder_id() {
		return Order_id;
	}

	public void setOrder_id(int Order_id) {
		this.Order_id = Order_id;
	}

	public LocalDateTime getPayment_date() {
		return Payment_date;
	}

	public void setPayment_date(LocalDateTime Payment_date) {
		this.Payment_date = Payment_date;
	}

	public double getAmount() {
		return Amount;
	}

	public void setAmount(double Amount) {
		this.Amount = Amount;
	}

	public PaymentStatus getStatus() {
		return Status;
	}

	public void setStatus(PaymentStatus Status) {
		this.Status = Status;
	}

}
