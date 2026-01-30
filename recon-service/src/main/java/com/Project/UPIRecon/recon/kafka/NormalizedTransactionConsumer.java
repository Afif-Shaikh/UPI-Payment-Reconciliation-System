package com.Project.UPIRecon.recon.kafka;

//import com.Project.UPIRecon.recon.dto.NormalizedTransactionDTO;
import com.Project.UPIRecon.recon.service.NormalizedTransactionService;
import com.Project.UPIRecon.config.LoggingConfig;
import com.Project.UPIRecon.dto.NormalizedTransactionEvent;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class NormalizedTransactionConsumer {

	private static final Logger log = LoggingConfig.getLogger(NormalizedTransactionConsumer.class);

	@Autowired
	private NormalizedTransactionService normalizedTransactionService;

	@KafkaListener(topics = "normalized-transactions", groupId = "recon-group", containerFactory = "kafkaListenerContainerFactory")
	public void consume(NormalizedTransactionEvent  dto) {
		log.info("Consumed normalized transaction from Kafka. TransactionId: {}", dto.getTransactionId());

		try {
			normalizedTransactionService.saveNormalizedTransaction(dto);
			log.info("Successfully saved normalized transaction. TransactionId: {}", dto.getTransactionId());
		} catch (Exception e) {
			log.error("Failed to save normalized transaction. TransactionId: {} | Error: {}", dto.getTransactionId(),
					e.getMessage(), e);
		}
	}
}
