package com.example.services;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Customer;
import com.example.repository.CustomerRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public void sendInvoiceEmail(int userId, File invoicePdf) throws Exception {

        Customer customer = customerRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(customer.getEmail());
        helper.setSubject("Your Invoice");
        helper.setText(
                "Dear " + customer.getFullName() + ",\n\n" +
                "Please find your invoice attached.\n\nThank you for your purchase!"
        );

        helper.addAttachment("invoice.pdf", invoicePdf);

        mailSender.send(message);
    }

    @Override
    public void sendPromotionalEmail(int userId) {

        Customer customer = customerRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        if (!customer.isPromotionalEmail()) {
            return; // user did not opt in
        }

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(customer.getEmail());
        mail.setSubject("🔥 Exclusive Offer Just for You!");
        mail.setText(
                "Hello " + customer.getFullName() + ",\n\n" +
                "We have exciting offers waiting for you.\n" +
                "Check them out today!"
        );

        mailSender.send(mail);
    }
}


