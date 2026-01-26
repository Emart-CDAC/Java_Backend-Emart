package com.example.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Invoice;
import com.example.model.Orders;
import com.example.model.PaymentMethod;
import com.example.repository.InvoiceRepository;
import com.example.repository.OrdersRepository;

@Service
public class InvoiceServiceImp implements InvoiceService {

	@Autowired
	private InvoiceRepository invoiceRepo;

	@Autowired
	private OrdersRepository ordersRepo;

	@Override
	public Invoice addInvoice(int orderId) {

		Orders order = ordersRepo.findById(orderId)
				.orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));

		Invoice invoice = new Invoice();

		invoice.setOrder(order);
		invoice.setCustomer(order.getCustomer());
		invoice.setOrderDate(order.getOrderDate());

		// Logic Update for Pricing
		com.example.model.Cart cart = order.getCart();
		if (cart != null) {
			invoice.setDiscountAmount(cart.getEpointDiscount() + cart.getCouponDiscount());
			invoice.setTotalAmount(cart.getFinalPayableAmount());
			invoice.setEpointsUsed(cart.getUsedEpoints());
			invoice.setEpointsEarned(cart.getEarnedEpoints());
		} else {
			// Fallback if cart not present (should generally not happen if Order linked to
			// Cart)
			invoice.setDiscountAmount(0.0);
			invoice.setTotalAmount(order.getTotalAmount() != null ? order.getTotalAmount().doubleValue() : 0.0);
			invoice.setEpointsUsed(0);
			invoice.setEpointsEarned(0);
		}
		invoice.setEpointsBalance(0);

		if (order.getAddress() != null) {
			invoice.setBillingAddress(order.getAddress().toString());
			invoice.setShippingAddress(order.getAddress().toString());
		} else {
			invoice.setBillingAddress("N/A");
			invoice.setShippingAddress("N/A");
		}

		return invoiceRepo.save(invoice);
	}

	@Override
	public Optional<Invoice> findById(int id) {
		return invoiceRepo.findById(id);
	}

	@Override
	public List<Invoice> findAll() {
		return invoiceRepo.findAll();
	}
}
