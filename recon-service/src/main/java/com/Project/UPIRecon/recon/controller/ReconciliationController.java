package com.Project.UPIRecon.recon.controller;

import com.Project.UPIRecon.recon.entity.ReconciliationResult;
import com.Project.UPIRecon.recon.repository.ReconciliationResultRepository;
import com.Project.UPIRecon.recon.service.ReconciliationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reconciliation")
public class ReconciliationController {

    @Autowired
    private ReconciliationService reconciliationService;

    @Autowired
    private ReconciliationResultRepository resultRepository;

    // Endpoint to manually trigger reconciliation
    @PostMapping("/run")
    public String triggerReconciliation() {
        reconciliationService.reconcileTransactions();
        return "Reconciliation triggered successfully.";
    }

    // Endpoint to get all reconciliation results
    @GetMapping("/results")
    public List<ReconciliationResult> getAllResults() {
        return resultRepository.findAll();
    }
}
