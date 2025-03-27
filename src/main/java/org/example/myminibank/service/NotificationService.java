package org.example.myminibank.service;

import org.example.myminibank.events.NotificationEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Value("${spring.mail.from:noreply@myminibank.com}")
    private String from;

    private final JavaMailSender mailSender;

    public NotificationService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @KafkaListener(topics = "notifications", groupId = "notifications-group")
    public void notify(NotificationEvent notificationEvent) {
        try {
            sendSimpleEmail(notificationEvent.getEmail(), notificationEvent.getSubject(), notificationEvent.getMessage());
        } catch (MailException e) {
            // save email and retry later for example
        }
    }

    public void sendSimpleEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

}
