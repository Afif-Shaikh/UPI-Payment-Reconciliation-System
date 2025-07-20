package com.Project.UPIRecon.normalizer.kafka;

import com.Project.UPIRecon.normalizer.dto.NormalizedTransactionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class TransactionProducer {

    private static final String TOPIC = "normalized-transactions";

    @Autowired
    private KafkaTemplate<String, NormalizedTransactionDTO> kafkaTemplate;

    public void sendTransaction(NormalizedTransactionDTO dto) {
        kafkaTemplate.send(TOPIC, dto.getTransactionId(), dto);
        System.out.println("Sent to Kafka: " + dto);
    }
}
