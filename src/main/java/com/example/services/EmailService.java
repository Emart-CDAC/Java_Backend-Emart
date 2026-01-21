package com.example.services;

import java.io.File;

public interface EmailService {

    // Send invoice PDF to customer email
    void sendInvoiceEmail(int userId, File invoicePdf) throws Exception;

    // Send promotional email if user opted in
    void sendPromotionalEmail(int userId);

//	void sendInvoiceEmail(int userId, File pdfFile);
}
