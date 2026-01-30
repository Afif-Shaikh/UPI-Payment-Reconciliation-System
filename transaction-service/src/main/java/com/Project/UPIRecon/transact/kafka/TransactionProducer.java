package com.Project.UPIRecon.transact.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.Project.UPIRecon.config.LoggingConfig;
import org.slf4j.Logger;
import com.Project.UPIRecon.transact.dto.TransactionEvent;

@Service
public class TransactionProducer {

	private static final Logger log = LoggingConfig.getLogger(TransactionProducer.class);
	private static final String TOPIC = "transactions";

	private final KafkaTemplate<String, TransactionEvent> kafkaTemplate;

	public TransactionProducer(KafkaTemplate<String, TransactionEvent> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	public void sendTransaction(TransactionEvent event) {
		try {
			kafkaTemplate.send(TOPIC, event);
			log.info("Transaction sent to Kafka. TransactionId={}, Sender={}, Receiver={}, Amount={}",
					event.getTransactionId(), event.getSender(), event.getReceiver(), event.getAmount());
		} catch (Exception e) {
			log.error("Failed to send transaction to Kafka. TransactionId={} | Error={}", event.getTransactionId(),
					e.getMessage(), e);
		}
	}
}
