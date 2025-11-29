package com.Project.UPIRecon.normalizer.kafka;

import com.Project.UPIRecon.dto.RawTransactionEvent;
import com.Project.UPIRecon.normalizer.service.NormalizerService;
import com.Project.UPIRecon.config.LoggingConfig;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class RawTransactionConsumer {

	private static final Logger log = LoggingConfig.getLogger(RawTransactionConsumer.class);

	@Autowired
	private NormalizerService normalizerService;

	@KafkaListener(topics = "raw-transactions", groupId = "normalizer-group")
	public void consume(RawTransactionEvent event) {
		log.info("Consumed RAW transaction from Kafka. TransactionId: {}", event.getTransactionId());

		try {
			normalizerService.normalize(event);
			log.info("Successfully normalized transaction. TransactionId: {}", event.getTransactionId());
		} catch (Exception e) {
			log.error("Failed to normalize transaction. TransactionId: {} | Error: {}", event.getTransactionId(),
					e.getMessage(), e);
		}
	}
}
