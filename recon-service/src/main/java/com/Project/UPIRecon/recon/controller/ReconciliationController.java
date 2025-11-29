package com.Project.UPIRecon.recon.controller;

import com.Project.UPIRecon.recon.entity.ReconciliationResult;
import com.Project.UPIRecon.config.LoggingConfig;
import org.slf4j.Logger;
import com.Project.UPIRecon.recon.repository.ReconciliationResultRepository;
import com.Project.UPIRecon.recon.service.ReconciliationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reconciliation")
public class ReconciliationController {

	private static final Logger log = LoggingConfig.getLogger(ReconciliationController.class);

	@Autowired
	private ReconciliationService reconciliationService;

	@Autowired
	private ReconciliationResultRepository resultRepository;

	// Endpoint to manually trigger reconciliation
	@PostMapping("/run")
	public String triggerReconciliation() {
		log.info("Starting manual reconciliation process");
		try {
			reconciliationService.reconcileTransactions();
			log.info("Manual reconciliation process completed successfully");
			return "Manual reconciliation completed successfully.";
		} catch (Exception e) {
			log.error("Manual reconciliation process failed. Error: {}", e.getMessage(), e);
			return "Manual reconciliation failed:" + e.getMessage();
		}
	}

	// Endpoint to get all reconciliation results
	@GetMapping("/results")
	public List<ReconciliationResult> getAllResults() {
		List<ReconciliationResult> results = resultRepository.findAll();
		log.info("Fetched {} reconciliation results", results.size());
		return results;
	}
}
