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
public class InvoiceServiceImp implements InvoiceService 
{
	@Autowired
	private final InvoiceRepository invoiceRepo=null;
	@Autowired
    private final OrdersRepository ordersRepo=null;
	
    @Override
    public Invoice addInvoice(int orderId) {

        Orders order = ordersRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));

        Invoice invoice = new Invoice();

        invoice.setOrder(order);                       
        invoice.setCustomer(order.getCustomer());       
        invoice.setOrderDate(order.getOrderDate());      

        invoice.setPaymentMethod(PaymentMethod.CASH);    // set based on requirement
        invoice.setDiscountAmount(7);		//temporary
        invoice.setTaxAmount(0.0);			//what to assign

        if (order.getTotalAmount() != null) {
            invoice.setTotalAmount(order.getTotalAmount().doubleValue());
        } else {
            invoice.setTotalAmount(0.0);
        }

        invoice.setDeliveryType(order.getDeliveryType());   // take delivery type from order

        invoice.setEpointsUsed(order.getEpointsUsed());
        invoice.setEpointsEarned(order.getEpointsEarned());

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
	public Optional<Invoice> findById(int id) 
	{
		return invoiceRepo.findById(id);
	}

	@Override
	public List<Invoice> findAll() 
	{
		return invoiceRepo.findAll();
	}



}
