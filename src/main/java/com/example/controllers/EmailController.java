package com.example.controllers;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.services.EmailService;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    // Send Invoice Email
    @PostMapping("/send-invoice/{userId}")
    public ResponseEntity<String> sendInvoice(@PathVariable int userId) {
        try {
            // Assume invoice PDF already generated
            File pdfFile = new File("D:/invoices/invoice_" + userId + ".pdf");

            emailService.sendInvoiceEmail(userId, pdfFile);
            return ResponseEntity.ok("Invoice email sent successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to send invoice email");
        }
    }

    // Send Promotional Email
    @PostMapping("/send-promo/{userId}")
    public ResponseEntity<String> sendPromo(@PathVariable int userId) {

        emailService.sendPromotionalEmail(userId);
        return ResponseEntity.ok("Promotional email processed");
    }
}
