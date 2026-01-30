package com.Project.UPIRecon.normalizer.kafka;

import com.Project.UPIRecon.dto.NormalizedTransactionEvent;
import com.Project.UPIRecon.config.LoggingConfig;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class TransactionProducer {

	private static final String TOPIC = "normalized-transactions";
	private static final Logger log = LoggingConfig.getLogger(TransactionProducer.class);

	@Autowired
	private KafkaTemplate<String, NormalizedTransactionEvent> kafkaTemplate;

	public void send(NormalizedTransactionEvent dto) {
		log.info("Preparing to send normalized transaction to Kafka. TransactionId: {}", dto.getTransactionId());
		log.debug("NormalizedTransactionEvent details: {}", dto);

		try {
			kafkaTemplate.send(TOPIC, dto.getTransactionId(), dto);
			log.info("Successfully sent normalized transaction to Kafka topic '{}'. TransactionId: {}", TOPIC,
					dto.getTransactionId());
		} catch (Exception e) {
			log.error("Failed to send normalized transaction to Kafka. TransactionId: {} | Error: {}",
					dto.getTransactionId(), e.getMessage(), e);
		}
		System.out.println("Sent to Kafka: " + dto);
	}
}
