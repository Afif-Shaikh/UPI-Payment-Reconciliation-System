package com.Project.UPIRecon.report.controller;

import com.Project.UPIRecon.report.entity.ReconciliationResult;
import com.Project.UPIRecon.report.service.ReportService;
import com.Project.UPIRecon.config.LoggingConfig;
import org.slf4j.Logger;
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

	private static final Logger log = LoggingConfig.getLogger(ReportController.class);

	@GetMapping
//	public List<ReconciliationResult> getReportByDate(
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
//		return results;
//		return reportService.getReconciliationResultsByDate(date);
	}
}
