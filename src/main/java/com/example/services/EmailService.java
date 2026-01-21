package com.example.services;

public interface EmailService {

	void sendPdf(String toEmail, byte[] pdfBytes);

}
