package com.Project.UPIRecon.ingest.kafka;

import com.Project.UPIRecon.dto.RawTransactionEvent;
import com.Project.UPIRecon.ingest.model.RawTransaction;
import com.Project.UPIRecon.config.LoggingConfig;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class RawTransactionProducer {

    private static final String TOPIC = "raw-transactions";
    private static final Logger log = LoggingConfig.getLogger(RawTransactionProducer.class);

    @Autowired
    private KafkaTemplate<String, RawTransactionEvent> kafkaTemplate;

    public void send(RawTransaction transaction) {
    	log.info("Preparing RawTransactionEvent for Kafka. TransactionId: {}", transaction.getTransactionId());
    	RawTransactionEvent event = new RawTransactionEvent();
        event.setTransactionId(transaction.getTransactionId());
        event.setAmount(transaction.getAmount());
        event.setSenderUpi(transaction.getSenderUpi());
        event.setReceiverUpi(transaction.getReceiverUpi());
        event.setTimestamp(transaction.getTimeStamp());
        log.debug("RawTransactionEvent details: {}", event);

        try {
			log.info("Sending RawTransactionEvent to Kafka topic: {}", TOPIC);
			kafkaTemplate.send(TOPIC, event.getTransactionId(), event);
			log.info("Successfully sent RawTransactionEvent to Kafka. TransactionId: {}", event.getTransactionId());
		} catch (Exception e) {
			log.error("Failed to send RawTransactionEvent to Kafka. TransactionId: {} | Error: {}",
                    event.getTransactionId(), e.getMessage(), e);
		}
    }
}
