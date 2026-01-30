package com.Project.UPIRecon.ingest.controller;

import com.Project.UPIRecon.ingest.kafka.RawTransactionProducer;
import com.Project.UPIRecon.ingest.model.RawTransaction;
import com.Project.UPIRecon.ingest.repository.RawTransactionRepository;
import com.Project.UPIRecon.ingest.service.ExcelParserService;
import com.Project.UPIRecon.ingest.service.RawTransactionService;

import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.responses.*;

import java.util.List;

import com.Project.UPIRecon.config.LoggingConfig;
import org.slf4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/ingest")
public class TransactionIngestController {

	private static final Logger log = LoggingConfig.getLogger(TransactionIngestController.class);

	@Autowired
	private RawTransactionRepository repository;

	@Autowired
	private ExcelParserService excelParserService;

	@Autowired
	private RawTransactionProducer producer;

	@Autowired
	private RawTransactionService rawTransactionService;

	@Operation(summary = "Ingest a single raw transaction", description = "Accepts a raw UPI transaction for a given source (BANK, NPCI, PSP, MERCHANT) and sends it to Kafka.")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Transaction ingested successfully"),
			@ApiResponse(responseCode = "400", description = "Validation failed") })
	
	@PostMapping("/{source}")
	public String ingestTransaction(@Parameter(description = "Transaction source, e.g. BANK or NPCI") @PathVariable String source, @Valid @RequestBody RawTransaction transaction) {
		log.info("Ingest Transaction API triggered. Source: {}", source);
		transaction.setSource(source);

		log.info("Saving transaction to database...");
		repository.save(transaction);
		log.info("Transaction saved to DB successfully. Transaction ID: {}", transaction.getTransactionId());

		log.info("Sending transaction to Kafka...");
		producer.send(transaction);
		log.info("Transaction successfully sent to Kafka topic.");
		return "Transaction ingested successfully!";
	}

	@PostMapping("/upload")
	public ResponseEntity<String> uploadRawTransactions(@RequestParam("file") MultipartFile file) {
		log.info("Upload Raw Transactions API triggered. File received: {}", file.getOriginalFilename());
		try {
			log.info("Parsing Excel file...");
			List<RawTransaction> transactions = excelParserService.parseExcel(file);
			log.info("Excel file parsed successfully. Total transactions found: {}", transactions.size());

			log.info("Saving transactions to database...");
			rawTransactionService.saveAll(transactions);
			log.info("Transactions saved successfully.");
			return ResponseEntity.ok("Successfully uploaded " + transactions.size() + " transactions");
		} catch (Exception e) {
			log.error("Error while processing the uploaded file: {}", e.getMessage(), e);
//            e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
		}
	}
}
