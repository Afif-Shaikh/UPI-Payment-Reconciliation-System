package com.Project.UPIRecon.ingest.controller;

import com.Project.UPIRecon.ingest.model.RawTransaction;
import com.Project.UPIRecon.ingest.repository.RawTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/ingest")
public class TransactionIngestController {

    @Autowired
    private RawTransactionRepository repository;

    @PostMapping("/{source}")
    public String ingestTransaction(@PathVariable String source, @Valid @RequestBody RawTransaction transaction) {
        transaction.setSource(source);
        repository.save(transaction);
        return "Transaction ingested successfully!";
    }
}
