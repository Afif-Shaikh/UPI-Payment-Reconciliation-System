package com.Project.UPIRecon.transact.controller;

import com.Project.UPIRecon.transact.dto.TransactionDTO;

import com.Project.UPIRecon.transact.entity.Transaction;
import com.Project.UPIRecon.transact.repository.TransactionRepository;
import com.Project.UPIRecon.transact.kafka.TransactionProducer;
import com.Project.UPIRecon.transact.dto.TransactionEvent;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/transactions")
@Validated
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepository;
    
    @Autowired
    private TransactionProducer transactionProducer;

    // POST /transactions - Adding a transaction
    @PostMapping
    public ResponseEntity<String> ingestTransaction(@Valid @RequestBody TransactionDTO transactionDTO) {
    	 if (transactionRepository.existsById(transactionDTO.getTransactionId())) {
    	        return ResponseEntity.status(HttpStatus.CONFLICT).body("Transaction ID already exists.");
    	    }
    	 
        Transaction transaction = new Transaction();
        transaction.setTransactionId(transactionDTO.getTransactionId());
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setTimestamp(transactionDTO.getTimestamp());
        transaction.setSender(transactionDTO.getSender());
        transaction.setReceiver(transactionDTO.getReceiver());
        
     // Send to Kafka
        TransactionEvent event = new TransactionEvent(
                transactionDTO.getTransactionId(),
                transactionDTO.getSender(),
                transactionDTO.getReceiver(),
                transactionDTO.getAmount(),
                transactionDTO.getTimestamp(),
                "Created"
        );
        transactionProducer.sendTransaction(event);

        transactionRepository.save(transaction);
        return ResponseEntity.ok("Transaction added successfully & Sent to Kafka!");
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
