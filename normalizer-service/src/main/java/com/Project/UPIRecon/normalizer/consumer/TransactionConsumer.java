package com.Project.UPIRecon.normalizer.consumer;

import com.Project.UPIRecon.normalizer.dto.TransactionKafkaEvent;
import com.Project.UPIRecon.normalizer.entity.NormalizedTransaction;
import com.Project.UPIRecon.normalizer.repository.NormalizedTransactionRepository;

import java.sql.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TransactionConsumer {

    @Autowired
    private NormalizedTransactionRepository repository;

    @KafkaListener(topics = "transactions", groupId = "normalizer-group", containerFactory = "kafkaListenerContainerFactory")
    public void consume(TransactionKafkaEvent event) {
        NormalizedTransaction normalized = new NormalizedTransaction();
        normalized.setTransactionId(event.getTransactionId());
//        normalized.setTransactionId(UUID.randomUUID().toString());
        normalized.setAmount(event.getAmount());
        normalized.setSenderUpi(event.getSender());
        normalized.setReceiverUpi(event.getReceiver());
        normalized.setTimestamp(event.getTimestamp());
//        normalized.setTransactionTime(Date);

        repository.save(normalized);
    }
}
