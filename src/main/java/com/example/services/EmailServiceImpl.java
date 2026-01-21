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
            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true);

            helper.setTo(toEmail);
            helper.setSubject("Invoice");
            helper.setText("Please find your invoice attached.");

            ByteArrayDataSource dataSource =
                    new ByteArrayDataSource(pdfBytes, "application/pdf");

            helper.addAttachment("invoice.pdf", dataSource);

            mailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException("Mail sending failed", e);
        }
    }
}
