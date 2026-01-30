package com.Project.UPIRecon.transact.controller;

import com.Project.UPIRecon.transact.dto.TransactionDTO;
import com.Project.UPIRecon.transact.entity.Transaction;
import com.Project.UPIRecon.transact.repository.TransactionRepository;
import com.Project.UPIRecon.transact.kafka.TransactionProducer;
import com.Project.UPIRecon.transact.dto.TransactionEvent;
import com.Project.UPIRecon.config.LoggingConfig;
import org.slf4j.Logger;

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

	private static final Logger log = LoggingConfig.getLogger(TransactionController.class);

	// POST /transactions - Adding a transaction
	@PostMapping
	public ResponseEntity<String> ingestTransaction(@Valid @RequestBody TransactionDTO transactionDTO) {
		log.info("Ingesting transaction with ID: {}", transactionDTO.getTransactionId());

		try {
			if (transactionRepository.existsById(transactionDTO.getTransactionId())) {
				log.warn("Transaction ID already exists: {}", transactionDTO.getTransactionId());
				return ResponseEntity.status(HttpStatus.CONFLICT).body("Transaction ID already exists.");
			}

			Transaction transaction = new Transaction();
			transaction.setTransactionId(transactionDTO.getTransactionId());
			transaction.setAmount(transactionDTO.getAmount());
			transaction.setTimestamp(transactionDTO.getTimestamp());
			transaction.setSender(transactionDTO.getSender());
			transaction.setReceiver(transactionDTO.getReceiver());

			// Send to Kafka
			TransactionEvent event = new TransactionEvent(transactionDTO.getTransactionId(), transactionDTO.getSender(),
					transactionDTO.getReceiver(), transactionDTO.getAmount(), transactionDTO.getTimestamp(), "Created");
			transactionProducer.sendTransaction(event);
			log.info("Transaction sent to Kafka: {}", transactionDTO.getTransactionId());

			transactionRepository.save(transaction);
			log.info("Transaction saved to DB: {}", transactionDTO.getTransactionId());

			return ResponseEntity.ok("Transaction added successfully & Sent to Kafka!");
		} catch (Exception e) {
			log.error("Error ingesting transaction ID {} | Error: {}", transactionDTO.getTransactionId(),
					e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Failed to ingest transaction. Error: " + e.getMessage());
		}
	}

	// GET /transactions - List all transactions (with optional search and
	// pagination)
	@GetMapping
	public ResponseEntity<Page<Transaction>> getAllTransactions(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(required = false) String upiId) {

		log.info("Fetching transactions. Page: {}, Size: {}, UPI filter: {}", page, size, upiId);

		try {
			Pageable pageable = PageRequest.of(page, size);
			Page<Transaction> transactions;

			if (upiId != null && !upiId.isEmpty()) {
				transactions = transactionRepository.findBySenderOrReceiver(upiId, upiId, pageable);
				log.info("Fetched {} transactions filtered by UPI ID: {}", transactions.getTotalElements(), upiId);
			} else {
				transactions = transactionRepository.findAll(pageable);
				log.info("Fetched {} transactions filtered by UPI ID: {}", transactions.getTotalElements(), upiId);
			}

			return ResponseEntity.ok(transactions);
		} catch (Exception e) {
			log.error("Error fetching transactions | Error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Page.empty());
		}
	}
}
