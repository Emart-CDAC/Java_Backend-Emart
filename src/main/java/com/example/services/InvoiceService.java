package com.example.services;

import com.example.model.Invoice;

public interface InvoiceService 
{
	Invoice addInvoice(int order_id);
	Invoice findById(int invoice_id);
	
}
