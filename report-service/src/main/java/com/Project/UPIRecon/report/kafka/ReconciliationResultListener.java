package com.Project.UPIRecon.report.kafka;

import com.Project.UPIRecon.report.entity.ReconciliationResult;
import com.Project.UPIRecon.report.repository.ReconciliationResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.Project.UPIRecon.config.LoggingConfig;
import com.Project.UPIRecon.dto.ReconciliationResultEvent;

import org.slf4j.Logger;

@Service
public class ReconciliationResultListener {

	@Autowired
	private ReconciliationResultRepository repository;
	private static final Logger log = LoggingConfig.getLogger(ReconciliationResultListener.class);

	@KafkaListener(topics = "reconciliation_result", groupId = "report-service")
	public void consume(ReconciliationResultEvent result) {
		log.info("Received reconciliation result from Kafka: key={}, status={}, amount={}", result.getNormalizedKey(),
				result.getStatus(), result.getAmount());
		log.debug("Full ReconciliationResult DTO: {}", result);
		ReconciliationResult existing =
	            repository.findByNormalizedKeyAndStatus(result.getNormalizedKey(), result.getStatus());
		
		if (existing != null) {
	        log.info("Duplicate detected → Updating existing record: key={}, status={}",
	        		result.getNormalizedKey(), result.getStatus());

	        existing.setAmount(result.getAmount());
	        existing.setSenderUpi(result.getSenderUpi());
	        existing.setReceiverUpi(result.getReceiverUpi());
	        existing.setTransactionTime(result.getTransactionTime());
	        existing.setRemarks(result.getRemarks());
	        existing.setTransactionCount(result.getTransactionCount());

	        repository.save(existing);
	        return;
	    }

		try {
			ReconciliationResult entity = new ReconciliationResult();
		    entity.setId(result.getId());
		    entity.setNormalizedKey(result.getNormalizedKey());
		    entity.setAmount(result.getAmount());
		    entity.setSenderUpi(result.getSenderUpi());
		    entity.setReceiverUpi(result.getReceiverUpi());
		    entity.setTransactionTime(result.getTransactionTime());
		    entity.setStatus(result.getStatus());
		    entity.setRemarks(result.getRemarks());
		    entity.setTransactionCount(result.getTransactionCount());
			repository.save(entity);
			log.info("Consumed reconciliation result from Kafka: key={}, status={}, amount={}",
					result.getNormalizedKey(), result.getStatus(), result.getAmount());
		} catch (Exception e) {
			log.error("Failed to save reconciliation result: key={} | Error: {}", result.getNormalizedKey(),
					e.getMessage(), e);
		}
	}
}
