package com.example.services;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.DeliveryType;
import com.example.model.Invoice;
import com.example.model.Orders;
import com.example.model.PaymentMethod;
import com.example.repository.InvoiceRepository;
import com.example.repository.OrderRepository;

@Service
public class InvoiceServiceImp implements InvoiceService 
{
	@Autowired
	private final InvoiceRepository invoiceRepo = null;
	@Autowired
	private final OrderRepository orderRepo=null;
	
	@Override
	public Invoice addInvoice(int order_id) {
		
//		Invoice invoice = ir.save(order_id);
		 Orders order = orderRepo.findById(order_id)
	                .orElseThrow(() -> new RuntimeException("Order not found: " + order_id));

	        Invoice invoice = new Invoice();

	        invoice.setOrder(order);                    // ✅ set FK relation
	        invoice.setCustomer(order.getCustomer());   // ✅ if order contains customer
	        invoice.setOrder_date(new Date());          // ✅ current date/time

	        // ✅ set enum values
	        invoice.setPayment_method(PaymentMethod.CASH);
	        invoice.setDelivery_type(DeliveryType.STORE);

	        // ✅ set amounts (example)
	        invoice.setDiscount_amount(new BigDecimal("0.00"));
	        invoice.setTax_amount(new BigDecimal("0.00"));
	        invoice.getTotal_amount((BigDecimal)order.getTotalAmount()); // if you have total in order

	        // ✅ set addresses
	        invoice.setBilling_address("N/A");
	        invoice.setShipping_address("N/A");

	        // ✅ points
	        invoice.setEpoints_used(0);
	        invoice.setEpoints_balance(0);		//noo
	        invoice.setEpoints_earned(0);

	        return invoiceRepo.save(invoice); // ✅ insert invoice row in DB
		
		return null;
	}

	@Override
	public Invoice findById(int invoice_id) {
		// TODO Auto-generated method stub
		return null;
	}



}
