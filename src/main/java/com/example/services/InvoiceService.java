package com.example.services;

import java.util.List;
import java.util.Optional;

import com.example.model.Invoice;

public interface InvoiceService 
{
	Invoice addInvoice(int order_id);
//	Optional<Invoice> findById(int invoice_id);
//	List<Invoice> findAll();
}
