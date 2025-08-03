package com.Project.UPIRecon.ingest.controller;

import com.Project.UPIRecon.ingest.model.RawTransaction;
import com.Project.UPIRecon.ingest.repository.RawTransactionRepository;
import com.Project.UPIRecon.ingest.service.ExcelParserService;
import com.Project.UPIRecon.ingest.service.RawTransactionService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/ingest")
public class TransactionIngestController {

    @Autowired
    private RawTransactionRepository repository;
    
    @Autowired
    private ExcelParserService excelParserService;

    @Autowired
    private RawTransactionService rawTransactionService;

    @PostMapping("/{source}")
    public String ingestTransaction(@PathVariable String source, @Valid @RequestBody RawTransaction transaction) {
        transaction.setSource(source);
        repository.save(transaction);
        return "Transaction ingested successfully!";
    }
    @PostMapping("/upload")
    public ResponseEntity<String> uploadRawTransactions(@RequestParam("file") MultipartFile file) {
        try {
            List<RawTransaction> transactions = excelParserService.parseExcel(file);
            rawTransactionService.saveAll(transactions);
            return ResponseEntity.ok("Successfully uploaded " + transactions.size() + " transactions");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

}
