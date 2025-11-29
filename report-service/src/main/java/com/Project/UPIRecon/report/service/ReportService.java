package com.Project.UPIRecon.report.service;

import com.Project.UPIRecon.report.entity.ReconciliationResult;
import com.Project.UPIRecon.report.repository.ReconciliationResultRepository;
import com.Project.UPIRecon.config.LoggingConfig;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReportService {

	@Autowired
	private ReconciliationResultRepository resultRepository;
	private static final Logger log = LoggingConfig.getLogger(ReportService.class);

	public List<ReconciliationResult> getReconciliationResultsByDate(LocalDate date) {
		log.info("Fetching reconciliation results for date: {}", date);
		List<ReconciliationResult> results;

//        LocalDateTime startOfDay;
//		LocalDateTime endOfDay;
		try {
			LocalDateTime startOfDay = date.atStartOfDay();
			LocalDateTime endOfDay = startOfDay.plusDays(1);
			results = resultRepository.findByTransactionTimeBetween(startOfDay, endOfDay);
			log.info("Fetched {} reconciliation results for date: {}", results.size(), date);
		} catch (Exception e) {
			log.error("Error fetching reconciliation results for date: {} | Error: {}", date, e.getMessage(), e);
			throw e; // propagate exception to controller
		}
		return results;
//        return resultRepository.findByTransactionTimeBetween(startOfDay, endOfDay);
	}
}
