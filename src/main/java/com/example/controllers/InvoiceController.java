package com.example.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Invoice;
import com.example.model.OrderItems;
import com.example.repository.InvoiceRepository;
import com.example.repository.OrderItemRepository;
import com.example.services.EmailService;
import com.example.services.InvoiceService;
import com.lowagie.text.DocumentException;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/invoice")
public class InvoiceController {

        @Autowired
        private InvoiceService invoiceService;

        @Autowired
        private InvoiceRepository invoiceRepo;

        @Autowired
        private OrderItemRepository orderItemsRepo;

        @Autowired
        private EmailService emailService;

        @PostMapping("/generate/{orderId}")
        public ResponseEntity<Invoice> generateInvoice(@PathVariable int orderId) {
                return ResponseEntity.ok(invoiceService.addInvoice(orderId));
        }

        @GetMapping("/view/{invoiceId}")
        public ResponseEntity<Invoice> viewInvoiceById(@PathVariable int invoiceId) {
                return invoiceService.findById(invoiceId)
                                .map(ResponseEntity::ok)
                                .orElse(ResponseEntity.notFound().build());
        }

        @GetMapping("/mail/latest")
        public ResponseEntity<String> mailLatestInvoice() {

                Invoice invoice = invoiceRepo.findTopByOrderByInvoiceIdDesc();
                if (invoice == null) {
                        return ResponseEntity.badRequest().body("No invoice");
                }

                List<OrderItems> items = orderItemsRepo.findByOrder_OrderId(
                                invoice.getOrder().getOrderId());

                InvoicePDFExporter exporter = new InvoicePDFExporter(invoice, items);

                byte[] pdf = exporter.generatePdfBytes();

                emailService.sendPdf(
                                invoice.getCustomer().getEmail(),
                                "Invoice Copy",
                                "Please find your invoice attached.",
                                pdf);

                return ResponseEntity.ok("Invoice mailed");
        }

        @GetMapping("/export/pdf/{invoiceId}")
        public void exportInvoicePdf(
                        @PathVariable int invoiceId,
                        HttpServletResponse response) throws IOException, DocumentException {

                Invoice invoice = invoiceService.findById(invoiceId)
                                .orElseThrow(() -> new RuntimeException("Invoice not found"));

                List<OrderItems> items = orderItemsRepo.findByOrder_OrderId(
                                invoice.getOrder().getOrderId());

                response.setContentType("application/pdf");
                response.setHeader(
                                "Content-Disposition",
                                "inline; filename=invoice_" + invoiceId + ".pdf");

                InvoicePDFExporter exporter = new InvoicePDFExporter(invoice, items);

                exporter.export(response);
        }
}
