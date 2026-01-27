package com.example.services;

public interface EmailService {
	void sendPdf(String toEmail, byte[] pdfBytes);

	void sendEmail(String toEmail, String subject, String body);
}
