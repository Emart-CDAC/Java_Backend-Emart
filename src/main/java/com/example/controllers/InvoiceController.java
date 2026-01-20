package com.example.controllers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient.ResponseSpec;

import com.example.model.Invoice;
import com.example.model.Product;
import com.example.services.InvoiceService;
import com.lowagie.text.DocumentException;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/invoice")
public class InvoiceController 
{
	@Autowired
	private final InvoiceService invoiceService;
	
	
	@PostMapping("/generate")
	public ResponseEntity<Invoice> generateInvoice(@PathVariable int order_id)
	{
		Invoice invoice = invoiceService.addInvoice(order_id);
		return ResponseEntity.ok(invoice);
	}
	
	@GetMapping("/view")
	public ResponseEntity<Invoice> viewInvoiceById(@PathVariable int invoice_id)
	{
		Invoice invoice = invoiceService.viewInvoiceById(invoice_id);
		return ResponseEntity.ok(invoice);
	}
	
	@GetMapping("/export/pdf")
    public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
         
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Products_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
         
        List<Invoice> invoice = invoiceService.findAll();
         
        InvoicePDFExporter exporter = new InvoicePDFExporter(invoice);
        exporter.export(response);
         
    }
	
}
