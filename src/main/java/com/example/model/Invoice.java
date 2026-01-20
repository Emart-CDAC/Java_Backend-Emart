package com.example.model;
import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;




@Entity
public class Invoice 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int invoice_id;
	
	@OneToOne

	@JoinColumn(name="order_id")
	private Orders orders;

	private Orders order;

	
	@OneToOne
	@JoinColumn(name="user_id")
	private Customer customer;
	
	private Date order_date;
	
	@Enumerated(EnumType.STRING)
	private String payment_method;		//Enum
	
	@Column(name="discount_amount", precision = 10, scale = 2)
	private BigDecimal discount_amount;
	
	@Column(name="tax_amount", precision = 10, scale = 2)
	private BigDecimal tax_amount;
	
	@Column(name="total_amount", precision = 10, scale = 2)
	private BigDecimal total_amount;
	
	@Enumerated(EnumType.STRING)
	private String delivery_type;		//enum
	
	private String billing_address;
	
	private String shipping_address;
	
	private int epoints_used;
	
	private int epoints_balance;
	
	private int epoints_earned;

	public int getInvoice_id() {
		return invoice_id;
	}

	public void setInvoice_id(int invoice_id) {
		this.invoice_id = invoice_id;
	}

	public Orders getOrder() {
		return order;
	}

	public void setOrder(Orders order) {
		this.order = order;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Date getOrder_date() {
		return order_date;
	}

	public void setOrder_date(Date order_date) {
		this.order_date = order_date;
	}

	public String getPayment_method() {
		return payment_method;
	}

	public void setPayment_method(String payment_method) {
		this.payment_method = payment_method;
	}

	public BigDecimal getDiscount_amount() {
		return discount_amount;
	}

	public void setDiscount_amount(BigDecimal discount_amount) {
		this.discount_amount = discount_amount;
	}

	public BigDecimal getTax_amount() {
		return tax_amount;
	}

	public void setTax_amount(BigDecimal tax_amount) {
		this.tax_amount = tax_amount;
	}

	public BigDecimal getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(BigDecimal total_amount) {
		this.total_amount = total_amount;
	}

	public String getDelivery_type() {
		return delivery_type;
	}

	public void setDelivery_type(String delivery_type) {
		this.delivery_type = delivery_type;
	}

	public String getBilling_address() {
		return billing_address;
	}

	public void setBilling_address(String billing_address) {
		this.billing_address = billing_address;
	}

	public String getShipping_address() {
		return shipping_address;
	}

	public void setShipping_address(String shipping_address) {
		this.shipping_address = shipping_address;
	}

	public int getEpoints_used() {
		return epoints_used;
	}

	public void setEpoints_used(int epoints_used) {
		this.epoints_used = epoints_used;
	}

	public int getEpoints_balance() {
		return epoints_balance;
	}

	public void setEpoints_balance(int epoints_balance) {
		this.epoints_balance = epoints_balance;
	}

	public int getEpoints_earned() {
		return epoints_earned;
	}

	public void setEpoints_earned(int epoints_earned) {
		this.epoints_earned = epoints_earned;
	}

	@Override
	public String toString() {
		return "Invoice [invoice_id=" + invoice_id + ", order_date=" + order_date + ", payment_method=" + payment_method
				+ ", discount_amount=" + discount_amount + ", tax_amount=" + tax_amount + ", total_amount="
				+ total_amount + ", delivery_type=" + delivery_type + ", billing_address=" + billing_address
				+ ", shipping_address=" + shipping_address + ", epoints_used=" + epoints_used + ", epoints_balance="
				+ epoints_balance + ", epoints_earned=" + epoints_earned + "]";
	}
	
	
}
