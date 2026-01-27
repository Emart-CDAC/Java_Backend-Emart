package com.example.services;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import jakarta.activation.DataHandler;
import jakarta.mail.util.ByteArrayDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendPdf(String toEmail, byte[] pdfBytes) {

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(toEmail);
            helper.setSubject("Invoice");
            helper.setText("Please find your invoice attached.");

            ByteArrayDataSource dataSource = new ByteArrayDataSource(pdfBytes, "application/pdf");

            helper.addAttachment("invoice.pdf", dataSource);

            mailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException("Mail sending failed", e);
        }
    }

    @Override
    public void sendEmail(String toInfo, String subject, String body) {
        try {
            org.springframework.mail.SimpleMailMessage mail = new org.springframework.mail.SimpleMailMessage();
            mail.setTo(toInfo);
            mail.setSubject(subject);
            mail.setText(body);

            System.out.println("📧 Sending email to: " + toInfo);
            mailSender.send(mail);
            System.out.println("✅ Email sent successfully.");
        } catch (Exception e) {
            System.err.println("❌ Error sending email: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
