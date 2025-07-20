package com.Project.UPIRecon.recon.service;

import com.Project.UPIRecon.recon.dto.NormalizedTransactionDTO;
import com.Project.UPIRecon.recon.entity.ReconciliationResult;
import com.Project.UPIRecon.recon.repository.ReconciliationResultRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReconciliationService {

    private final NormalizedTransactionService normalizedTransactionService;
    private final ReconciliationResultRepository resultRepository;

    public ReconciliationService(NormalizedTransactionService normalizedTransactionService,
                                 ReconciliationResultRepository resultRepository) {
		this.normalizedTransactionService = normalizedTransactionService;
        this.resultRepository = resultRepository;
    }

    // Run every 10 minutes or manually trigger this method in a controller if needed
    @Scheduled(fixedRate = 600000)
    public void reconcileTransactions() {
        List<NormalizedTransactionDTO> transactions = normalizedTransactionService.getAllNormalizedTransactions();

        System.out.println("Fetched transactions count: " + transactions.size());

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
            
            if (group == null || group.isEmpty()) continue;
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
        }
    }

    private String generateReconciliationKey(NormalizedTransactionDTO txn) {
        return txn.getSenderUpi() + "|" + txn.getReceiverUpi() + "|" +
               txn.getAmount() + "|" + txn.getTimestamp();
    }
}
