package org.example.notifyservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class EmailService {
    private final JavaMailSender mailSender;
    private final String from;

    public EmailService(
            JavaMailSender mailSender,
            @Value("${notification.mail.from}") String from) {
        this.mailSender = mailSender;
        this.from = from;
    }

    public void sendOrderCreatedEmail(String recipient) {
        if (!StringUtils.hasText(recipient)) {
            return;
        }
        if (!StringUtils.hasText(from)) {
            throw new IllegalStateException("Mail sender is not configured");
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(recipient.trim());
        message.setSubject("Đặt vé thành công");
        message.setText("Đơn đặt vé của bạn đã được tạo thành công.");

        mailSender.send(message);
    }
}
