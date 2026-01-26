package com.example.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Payment")
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Payment_id")
	private int paymentId;

	@Column(name = "Transaction_id")
	private String transactionId;

	@ManyToOne
	@JoinColumn(name = "order_id")
	private Orders order;

	@Column(name = "Payment_date")
	private LocalDateTime paymentDate;

	@Column(name = "Amount")
	private double amount;

	@Enumerated(EnumType.STRING)
	private PaymentStatus status;

	@Enumerated(EnumType.STRING)
	private PaymentMethod mode;
	
	//---------------------razorPay----------------------------
	
	// ✅ Razorpay Fields (NEW)
    @Column(name = "razorpay_order_id")
    private String razorpayOrderId;

    @Column(name = "razorpay_payment_id")
    private String razorpayPaymentId;

    @Column(name = "razorpay_signature")
    private String razorpaySignature;
    
    //----------------------------------------------------

	public int getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}

	public Orders getOrder() {
		return order;
	}

	public void setOrder(Orders order) {
		this.order = order;
	}

	public LocalDateTime getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(LocalDateTime paymentDate) {
		this.paymentDate = paymentDate;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public PaymentStatus getStatus() {
		return status;
	}

	public void setStatus(PaymentStatus status) {
		this.status = status;
	}

	public PaymentMethod getPaymentMethod() {
		return mode;
	}

	public void setPaymentMethod(PaymentMethod mode) {
		this.mode = mode;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	
	//-------------------------setter & Getter---------------------------------------
	

    // ✅ Razorpay getters/setters (NEW)
    public String getRazorpayOrderId() {
        return razorpayOrderId;
    }

    public void setRazorpayOrderId(String razorpayOrderId) {
        this.razorpayOrderId = razorpayOrderId;
    }

    public String getRazorpayPaymentId() {
        return razorpayPaymentId;
    }

    public void setRazorpayPaymentId(String razorpayPaymentId) {
        this.razorpayPaymentId = razorpayPaymentId;
    }

    public String getRazorpaySignature() {
        return razorpaySignature;
    }

    public void setRazorpaySignature(String razorpaySignature) {
        this.razorpaySignature = razorpaySignature;
    }
	
    //-----------------------------------------------
}
