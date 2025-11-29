package com.Project.UPIRecon.recon.service;

import com.Project.UPIRecon.recon.dto.NormalizedTransactionDTO;
import com.Project.UPIRecon.recon.entity.NormalizedTransaction;
import com.Project.UPIRecon.recon.repository.NormalizedTransactionRepository;
import com.Project.UPIRecon.config.LoggingConfig;
import org.slf4j.Logger;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NormalizedTransactionService {

	private static final Logger log = LoggingConfig.getLogger(NormalizedTransactionService.class);

	@Autowired
	private NormalizedTransactionRepository repository;

	public void saveNormalizedTransaction(NormalizedTransactionDTO dto) {
		try {
			NormalizedTransaction entity = new NormalizedTransaction();
			entity.setTransactionId(dto.getTransactionId());
			entity.setSender(dto.getSenderUpi());
			entity.setReceiver(dto.getReceiverUpi());
			entity.setAmount(dto.getAmount());
			entity.setTimestamp(dto.getTimestamp());
			repository.save(entity);
			log.info("Saved normalized transaction. TransactionId: {}", dto.getTransactionId());
		} catch (Exception e) {
			log.error("Failed to save normalized transaction. TransactionId: {} | Error: {}", dto.getTransactionId(),
					e.getMessage(), e);
		}
	}

	public List<NormalizedTransactionDTO> getAllNormalizedTransactions() {
		List<NormalizedTransaction> transactions = repository.findAll();
		log.info("Fetched {} normalized transactions from DB", transactions.size());

		return transactions.stream().map(txn -> {
			NormalizedTransactionDTO dto = new NormalizedTransactionDTO();
			dto.setSenderUpi(txn.getSender());
			dto.setReceiverUpi(txn.getReceiver());
			dto.setAmount(txn.getAmount());
			dto.setTimestamp(txn.getTimestamp());
			return dto;
		}).collect(Collectors.toList());
	}

}
