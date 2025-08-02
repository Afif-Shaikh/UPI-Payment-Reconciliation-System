package com.Project.UPIRecon.recon.kafka;

import com.Project.UPIRecon.recon.dto.NormalizedTransactionDTO;
import com.Project.UPIRecon.recon.service.NormalizedTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class NormalizedTransactionConsumer {

    @Autowired
    private NormalizedTransactionService normalizedTransactionService;

    @KafkaListener(topics = "normalized-transactions", groupId = "recon-group", containerFactory = "kafkaListenerContainerFactory")
    public void consume(NormalizedTransactionDTO dto) {
//        System.out.println("Received from Kafka: " + dto);
        normalizedTransactionService.saveNormalizedTransaction(dto);
    }
}
