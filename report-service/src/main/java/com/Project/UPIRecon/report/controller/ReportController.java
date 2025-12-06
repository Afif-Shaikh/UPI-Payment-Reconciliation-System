package com.Project.UPIRecon.report.controller;

import com.Project.UPIRecon.report.entity.ReconciliationResult;
import com.Project.UPIRecon.report.repository.ReconciliationResultRepository;
import com.Project.UPIRecon.report.service.ReportService;
import com.Project.UPIRecon.config.LoggingConfig;
import org.slf4j.Logger;

import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.*;
import io.swagger.v3.oas.annotations.media.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/report")
public class ReportController {

	@Autowired
	private ReportService reportService;
	
    @Autowired
    private ReconciliationResultRepository repository;

	private static final Logger log = LoggingConfig.getLogger(ReportController.class);

	@GetMapping
	@Operation(
            summary = "Get reconciliation report by date",
            description = "Returns all reconciliation results for the specified business date."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Report generated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReconciliationResult.class))),
            @ApiResponse(responseCode = "500", description = "Failed to generate report",
                    content = @Content(mediaType = "text/plain"))
    })
	public ResponseEntity<?> getReportByDate(
			@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
		log.info("Fetching reconciliation report for date: {}", date);
		List<ReconciliationResult> results;

		try {
			results = reportService.getReconciliationResultsByDate(date);
			log.info("Fetched {} reconciliation results for date: {}", results.size(), date);
			return ResponseEntity.ok(results);
		} catch (Exception e) {
			log.error("Error fetching reconciliation report for date: {} | Error: {}", date, e.getMessage(), e);
			return ResponseEntity.status(500)
					.body("Failed to fetch reconciliation report for date " + date + ". Error: " + e.getMessage());
		}
	}
	
	@GetMapping
    public List<ReconciliationResult> getAllReports() {
        return repository.findAll();
    }
	
	@GetMapping("/status/{status}")
    public List<ReconciliationResult> getByStatus(@PathVariable String status) {
        return repository.findByStatus(status);
    }
}
