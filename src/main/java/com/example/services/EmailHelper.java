package com.example.services;

import java.io.File;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

public class EmailHelper {

    private EmailHelper() {
        // utility class
    }

    public static void sendMailWithAttachment(
            JavaMailSender mailSender,
            String to,
            String subject,
            String body,
            File attachment
    ) throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body);
        helper.addAttachment(attachment.getName(), attachment);

        mailSender.send(message);
    }
}