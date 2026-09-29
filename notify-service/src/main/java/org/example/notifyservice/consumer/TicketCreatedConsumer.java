package org.example.notifyservice.consumer;

import org.example.notifyservice.service.EmailService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class TicketCreatedConsumer {
    private final EmailService emailService;

    public TicketCreatedConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(topics = "${notification.kafka.order-created-topic}")
    public void consume(String email) {
        if (!StringUtils.hasText(email)) {
            return;
        }

        emailService.sendOrderCreatedEmail(email);
    }
}
