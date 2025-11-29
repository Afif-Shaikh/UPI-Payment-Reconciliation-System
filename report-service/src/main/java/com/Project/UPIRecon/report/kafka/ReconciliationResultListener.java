package com.Project.UPIRecon.report.kafka;

import com.Project.UPIRecon.report.entity.ReconciliationResult;
import com.Project.UPIRecon.report.repository.ReconciliationResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.Project.UPIRecon.config.LoggingConfig;
import org.slf4j.Logger;

@Service
public class ReconciliationResultListener {

	@Autowired
	private ReconciliationResultRepository repository;
	private static final Logger log = LoggingConfig.getLogger(ReconciliationResultListener.class);

	@KafkaListener(topics = "reconciliation_result", groupId = "report-service")
	public void consume(ReconciliationResult result) {
		log.info("Received reconciliation result from Kafka: key={}, status={}, amount={}", result.getNormalizedKey(),
				result.getStatus(), result.getAmount());
		log.debug("Full ReconciliationResult DTO: {}", result);

		try {
			repository.save(result);
			log.info("Consumed reconciliation result from Kafka: key={}, status={}, amount={}",
					result.getNormalizedKey(), result.getStatus(), result.getAmount());
		} catch (Exception e) {
			log.error("Failed to save reconciliation result: key={} | Error: {}", result.getNormalizedKey(),
					e.getMessage(), e);
		}
	}
}
