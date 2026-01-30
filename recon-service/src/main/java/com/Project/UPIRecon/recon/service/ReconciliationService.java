package com.Project.UPIRecon.recon.service;

//import com.Project.UPIRecon.recon.dto.NormalizedTransactionDTO;
import com.Project.UPIRecon.recon.entity.NormalizedTransaction;
import com.Project.UPIRecon.recon.entity.ReconciliationResult;
import com.Project.UPIRecon.recon.repository.ReconciliationResultRepository;

import com.Project.UPIRecon.config.LoggingConfig;
import com.Project.UPIRecon.dto.ReconciliationResultEvent;

import org.slf4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReconciliationService {

	private final NormalizedTransactionService normalizedTransactionService;
	private final ReconciliationResultRepository resultRepository;
	private static final Logger log = LoggingConfig.getLogger(ReconciliationService.class);

	@Autowired
	private KafkaTemplate<String, ReconciliationResultEvent> kafkaTemplate;
	
	public ReconciliationService(NormalizedTransactionService normalizedTransactionService,
			ReconciliationResultRepository resultRepository) {
		this.normalizedTransactionService = normalizedTransactionService;
		this.resultRepository = resultRepository;
	}

	public void publishResult(ReconciliationResult result) {
		log.info("Publishing: normalizedKey={}, amount={}, status={}", result.getNormalizedKey(), 
				result.getAmount(), result.getStatus());
		log.debug("ReconciliationResult DTO: {}", result);

		try {
			ReconciliationResultEvent event = new ReconciliationResultEvent(
				    result.getId(),
				    result.getNormalizedKey(),
				    result.getAmount(),
				    result.getSenderUpi(),
				    result.getReceiverUpi(),
				    result.getTransactionTime(),
				    result.getStatus(),
				    result.getRemarks(),
				    result.getTransactionCount()
				);

				kafkaTemplate.send("reconciliation_result", event);

//			kafkaTemplate.send("reconciliation_result", result);
			log.info("Published reconciliation result to Kafka. normalizedKey={}", result.getNormalizedKey());
		} catch (Exception e) {
			log.error("Failed to publish reconciliation result. normalizedKey={} | Error: {}",
					result.getNormalizedKey(), e.getMessage(), e);
		}
	}


	// Run every 10 minutes or manually trigger this method in a controller if
	@Scheduled(fixedRate = 600_000)
	public void reconcileTransactions() {
		log.info("Starting reconciliation process");
		List<NormalizedTransaction> transactions = normalizedTransactionService.getAllNormalizedTransactions();
		log.info("Fetched {} normalized transactions for reconciliation", transactions.size());

	    // Group by normalizedKey
//		Map<String, List<NormalizedTransaction>> grouped =
//	            transactions.stream().collect(Collectors.groupingBy(NormalizedTransaction::getNormalizedKey));
		
		// Group by reconciliation key
        Map<String, List<NormalizedTransaction>> grouped = new HashMap<>();
        for (NormalizedTransaction txn : transactions) {
            String key = generateReconciliationKey(txn);
            grouped.computeIfAbsent(key, k -> new ArrayList<>()).add(txn);
            log.debug("Reconciliation key for txnId={} -> {}", txn.getTransactionId(), key);
        }

//		// Group by a unique reconciliation key
//		Map<String, List<NormalizedTransactionDTO>> grouped = new HashMap<>();
//		for (NormalizedTransactionDTO txn : transactions) {
//			String key = generateReconciliationKey(txn);
//			grouped.computeIfAbsent(key, k -> new ArrayList<>()).add(txn);
//		}

		// Process reconciliation logic
		for (Map.Entry<String, List<NormalizedTransaction>> entry : grouped.entrySet()) {
			String normalizedKey = entry.getKey();
			
			List<NormalizedTransaction> group = entry.getValue();
			if (group == null || group.isEmpty()) continue;			// Basic reference txn
			
			NormalizedTransaction ref = group.get(0);
	            
	            // Adding here reference only if we thinkg of changing the logics latter
	         // Basic logic: if more than 1 record for same key -> treat as matched pair/group
//	            if (group.size() > 1) {
//	                result.setStatus("MATCHED");
//	                result.setRemarks("Multiple transactions found with same normalized key");
//	            } else {
//	                result.setStatus("MISSING");
//	                result.setRemarks("Only one side of the transaction present for this key");
//	            }

	     // Count per source (uppercased to avoid case issues)
	        Map<String, Long> countBySource = group.stream()
	                .collect(Collectors.groupingBy(
	                        txn -> txn.getSource() == null ? "UNKNOWN" : txn.getSource().toUpperCase(),
	                        Collectors.counting()
	                ));
	        
	        long bankCount = countBySource.getOrDefault("BANK", 0L);
	        long npciCount = countBySource.getOrDefault("NPCI", 0L);
	        
	        String status;
	        String remarks;
	        
	        if (bankCount > 0 && npciCount > 0) {
	            status = "MATCHED";
	            remarks = "Present in BANK and NPCI";
	        } else if (bankCount > 0) {
	            status = "MISSING_AT_NPCI";
	            remarks = "Present only in BANK";
	        } else if (npciCount > 0) {
	            status = "MISSING_AT_BANK";
	            remarks = "Present only in NPCI";
	        } else {
	            status = "UNKNOWN_SOURCE";
	            remarks = "No BANK/NPCI record found";
	        }
	        
	        ReconciliationResult result = new ReconciliationResult();
            result.setNormalizedKey(normalizedKey);
            result.setAmount(ref.getAmount());
            result.setSenderUpi(ref.getSenderUpi());
            result.setReceiverUpi(ref.getReceiverUpi());
            result.setTransactionTime(ref.getTimestamp());
            result.setTransactionCount(group.size());
            result.setStatus(status);
            result.setRemarks(remarks);
            
			resultRepository.save(result);
			log.info("Saved reconciliation result to DB. normalizedKey={}, status={}", normalizedKey, result.getStatus());
			publishResult(result);
		}
		log.info("Reconciliation process completed successfully");
	}

	private String generateReconciliationKey(NormalizedTransaction txn) {
		return txn.getSenderUpi() + "|" + txn.getReceiverUpi() + "|" + txn.getAmount() + "|" + txn.getTimestamp();
	}
}
