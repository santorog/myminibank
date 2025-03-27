package org.example.myminibank.service;

import org.example.myminibank.events.NotificationEvent;
import org.example.myminibank.events.TransactionEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaPublisherService {

    private final KafkaTemplate<String, NotificationEvent> kafkaNotificationTemplate;
    private final KafkaTemplate<String, TransactionEvent> kafkaTransactionTemplate;

    public KafkaPublisherService(
            KafkaTemplate<String, NotificationEvent> kafkaNotificationTemplate,
            KafkaTemplate<String, TransactionEvent> kafkaTransactionTemplate) {

        this.kafkaNotificationTemplate = kafkaNotificationTemplate;
        this.kafkaTransactionTemplate = kafkaTransactionTemplate;
    }

    public void sendNotification(NotificationEvent event) {
        kafkaNotificationTemplate.send("notifications", event);
    }

    public void sendTransaction(TransactionEvent event) {
        kafkaTransactionTemplate.send("transactions", event);
    }
}
