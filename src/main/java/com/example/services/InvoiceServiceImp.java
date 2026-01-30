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
import com.lowagie.text.Paragraph;

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
			java.math.BigDecimal eDiscount = cart.getEpointDiscount();
			java.math.BigDecimal cDiscount = cart.getCouponDiscount();
			if (eDiscount == null)
				eDiscount = java.math.BigDecimal.ZERO;
			if (cDiscount == null)
				cDiscount = java.math.BigDecimal.ZERO;

			invoice.setDiscountAmount(eDiscount.add(cDiscount));
			invoice.setTotalAmount(cart.getFinalPayableAmount());
			invoice.setEpointsUsed(cart.getUsedEpoints());
			invoice.setEpointsEarned(cart.getEarnedEpoints());
		} else {
			// Fallback if cart not present (should generally not happen if Order linked to
			// Cart)
			invoice.setDiscountAmount(java.math.BigDecimal.ZERO);
			invoice.setTotalAmount(order.getTotalAmount() != null ? order.getTotalAmount() : java.math.BigDecimal.ZERO);
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

	@Override
	public byte[] generateInvoicePdf(Orders order) {
		try (java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream()) {
			com.lowagie.text.Document document = new com.lowagie.text.Document();
			com.lowagie.text.pdf.PdfWriter.getInstance(document, baos);

			document.open();

			com.lowagie.text.Font fontTitle = com.lowagie.text.FontFactory
					.getFont(com.lowagie.text.FontFactory.HELVETICA_BOLD);
			fontTitle.setSize(18);

			com.lowagie.text.Paragraph title = new com.lowagie.text.Paragraph("INVOICE", fontTitle);
			title.setAlignment(com.lowagie.text.Paragraph.ALIGN_CENTER);
			document.add(title);
			document.add(new Paragraph(" ")); // Spacer

			com.lowagie.text.Font fontHeader = com.lowagie.text.FontFactory
					.getFont(com.lowagie.text.FontFactory.HELVETICA_BOLD);
			document.add(new Paragraph("Order ID: " + order.getOrderId(), fontHeader));
			document.add(new Paragraph("Date: " + order.getOrderDate()));
			document.add(new Paragraph("Customer: " + order.getCustomer().getFullName()));
			document.add(new Paragraph("Email: " + order.getCustomer().getEmail()));
			document.add(new Paragraph("Payment Method: " + order.getPaymentMethod()));

			// if (order.getAddress() != null) {
			// document.add(new com.lowagie.text.Paragraph("Shipping Address: " +
			// order.getAddress().toString()));
			// }

			document.add(new com.lowagie.text.Paragraph(" "));

			// Table
			com.lowagie.text.pdf.PdfPTable table = new com.lowagie.text.pdf.PdfPTable(4);
			table.setWidthPercentage(100);
			table.setSpacingBefore(10f);
			table.setSpacingAfter(10f);

			// Headers
			table.addCell("Product");
			table.addCell("Quantity");
			table.addCell("Unit Price");
			table.addCell("Total");

			// Items
			for (com.example.model.OrderItems item : order.getOrderItems()) {
				table.addCell(item.getProduct().getName());
				table.addCell(String.valueOf(item.getQuantity()));
				table.addCell("Rs." + item.getPrice());
				table.addCell("Rs." + item.getSubtotal());
			}

			document.add(table);

			document.add(new com.lowagie.text.Paragraph("Total Amount: Rs." + order.getTotalAmount(), fontHeader));

			document.add(new com.lowagie.text.Paragraph(" "));
			document.add(new com.lowagie.text.Paragraph("Thank you for shopping with e-MART!"));

			document.close();

			return baos.toByteArray();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
