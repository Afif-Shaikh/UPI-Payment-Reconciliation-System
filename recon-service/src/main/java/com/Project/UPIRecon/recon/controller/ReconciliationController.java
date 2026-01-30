package com.Project.UPIRecon.recon.controller;

import com.Project.UPIRecon.recon.entity.ReconciliationResult;
import com.Project.UPIRecon.config.LoggingConfig;
import org.slf4j.Logger;
import com.Project.UPIRecon.recon.repository.ReconciliationResultRepository;
import com.Project.UPIRecon.recon.service.ReconciliationService;

import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.*;
import io.swagger.v3.oas.annotations.media.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reconciliation")
@Tag(name = "Reconciliation", description = "Endpoints to run reconciliation and view results")
public class ReconciliationController {

	private static final Logger log = LoggingConfig.getLogger(ReconciliationController.class);

	@Autowired
	private ReconciliationService reconciliationService;

	@Autowired
	private ReconciliationResultRepository resultRepository;

	// Endpoint to manually trigger reconciliation
	@PostMapping("/run")
	@Operation(
            summary = "Trigger reconciliation job",
            description = "Manually triggers the reconciliation process on all normalized transactions."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reconciliation completed successfully",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Reconciliation failed",
                    content = @Content(mediaType = "text/plain"))
    })
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
    @Operation(
            summary = "Get all reconciliation results",
            description = "Fetches all reconciliation results stored in the database."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reconciliation results fetched successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReconciliationResult.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
	public List<ReconciliationResult> getAllResults() {
		List<ReconciliationResult> results = resultRepository.findAll();
		log.info("Fetched {} reconciliation results", results.size());
		return results;
	}
}
