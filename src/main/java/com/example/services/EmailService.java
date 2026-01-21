package com.example.services;

import java.io.File;

public interface EmailService {

    void sendInvoiceEmail(int userId, File invoicePdf) throws Exception;

    void sendPromotionalEmail(int userId);

}
