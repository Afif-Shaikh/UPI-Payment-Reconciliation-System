package com.Project.UPIRecon.controller;

import com.Project.UPIRecon.dto.TransactionDTO;
import com.Project.UPIRecon.entity.Transaction;
import com.Project.UPIRecon.repository.TransactionRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepository;

    // POST /transactions - Ingest a transaction
    @PostMapping
    public ResponseEntity<String> ingestTransaction(@Valid @RequestBody TransactionDTO transactionDTO) {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(transactionDTO.getTransactionId());
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setTimestamp(transactionDTO.getTimestamp());
        transaction.setSender(transactionDTO.getSender());
        transaction.setReceiver(transactionDTO.getReceiver());

        transactionRepository.save(transaction);
        return ResponseEntity.ok("Transaction ingested successfully!");
    }

    // GET /transactions - List all transactions (with optional search and pagination)
    @GetMapping
    public ResponseEntity<Page<Transaction>> getAllTransactions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String upiId) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Transaction> transactions;
        if (upiId != null && !upiId.isEmpty()) {
            transactions = transactionRepository.findBySenderOrReceiver(upiId, upiId, pageable);
        } else {
            transactions = transactionRepository.findAll(pageable);
        }

        return ResponseEntity.ok(transactions);
    }
}
