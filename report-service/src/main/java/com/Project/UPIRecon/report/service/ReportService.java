package com.Project.UPIRecon.report.service;

import com.Project.UPIRecon.report.entity.ReconciliationResult;
import com.Project.UPIRecon.report.repository.ReconciliationResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReportService {

    @Autowired
    private ReconciliationResultRepository resultRepository;

    public List<ReconciliationResult> getReconciliationResultsByDate(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);

        return resultRepository.findByTransactionTimeBetween(startOfDay, endOfDay);
    }
}
