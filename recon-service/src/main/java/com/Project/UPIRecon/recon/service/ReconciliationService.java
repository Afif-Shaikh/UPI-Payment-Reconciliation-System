package com.Project.UPIRecon.recon.service;

import com.Project.UPIRecon.recon.dto.NormalizedTransactionDTO;
import com.Project.UPIRecon.recon.entity.ReconciliationResult;
import com.Project.UPIRecon.recon.repository.ReconciliationResultRepository;

import com.Project.UPIRecon.config.LoggingConfig;
import org.slf4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReconciliationService {

	private final NormalizedTransactionService normalizedTransactionService;
	private final ReconciliationResultRepository resultRepository;
	private static final Logger log = LoggingConfig.getLogger(ReconciliationService.class);

	@Autowired
	private KafkaTemplate<String, ReconciliationResult> kafkaTemplate;

	public void publishResult(ReconciliationResult result) {
		log.info("Publishing: normalizedKey={}, amount={}, status={}", result.getNormalizedKey(), result.getAmount(),
				result.getStatus());
		log.debug("ReconciliationResult DTO: {}", result);

		try {
			kafkaTemplate.send("reconciliation_result", result);
			log.info("Published reconciliation result to Kafka. normalizedKey={}", result.getNormalizedKey());
		} catch (Exception e) {
			log.error("Failed to publish reconciliation result. normalizedKey={} | Error: {}",
					result.getNormalizedKey(), e.getMessage(), e);
		}
	}

	public ReconciliationService(NormalizedTransactionService normalizedTransactionService,
			ReconciliationResultRepository resultRepository) {
		this.normalizedTransactionService = normalizedTransactionService;
		this.resultRepository = resultRepository;
	}

	// Run every 10 minutes or manually trigger this method in a controller if
	@Scheduled(fixedRate = 600000)
	public void reconcileTransactions() {
		log.info("Starting reconciliation process");
		List<NormalizedTransactionDTO> transactions = normalizedTransactionService.getAllNormalizedTransactions();
		log.info("Fetched {} normalized transactions for reconciliation", transactions.size());

		// Add this to inspect transaction keys
		for (NormalizedTransactionDTO txn : transactions) {
			String key = generateReconciliationKey(txn);
			log.debug("Reconciliation key for txnId={} -> {}", txn.getTransactionId(), key);
		}

		// Group by a unique reconciliation key
		Map<String, List<NormalizedTransactionDTO>> grouped = new HashMap<>();
		for (NormalizedTransactionDTO txn : transactions) {
			String key = generateReconciliationKey(txn);
			grouped.computeIfAbsent(key, k -> new ArrayList<>()).add(txn);
		}

		// Process reconciliation logic
		for (Map.Entry<String, List<NormalizedTransactionDTO>> entry : grouped.entrySet()) {
			String key = entry.getKey();
			List<NormalizedTransactionDTO> group = entry.getValue();

//            if (group == null || group.isEmpty()) continue;
			NormalizedTransactionDTO txn = group.get(0);

			ReconciliationResult result = new ReconciliationResult();
			result.setAmount(txn.getAmount());
			result.setSenderUpi(txn.getSenderUpi());
			result.setReceiverUpi(txn.getReceiverUpi());
			result.setTransactionTime(txn.getTimestamp());
			result.setNormalizedKey(key);
			result.setTransactionCount(group.size());
			result.setStatus(group.size() > 1 ? "MATCHED" : "MISSING");

			resultRepository.save(result);
			log.info("Saved reconciliation result to DB. normalizedKey={}, status={}", key, result.getStatus());
			publishResult(result);
		}
		log.info("Reconciliation process completed successfully");
	}

	private String generateReconciliationKey(NormalizedTransactionDTO txn) {
		return txn.getSenderUpi() + "|" + txn.getReceiverUpi() + "|" + txn.getAmount() + "|" + txn.getTimestamp();
	}
}
