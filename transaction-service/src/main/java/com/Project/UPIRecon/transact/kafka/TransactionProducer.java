package com.Project.UPIRecon.transact.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.Project.UPIRecon.transact.dto.TransactionEvent;

@Service
public class TransactionProducer {

    private final KafkaTemplate<String, TransactionEvent> kafkaTemplate;

    public TransactionProducer(KafkaTemplate<String, TransactionEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendTransaction(TransactionEvent event) {
        kafkaTemplate.send("transactions", event);
    }
}
