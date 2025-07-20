package com.Project.UPIRecon.recon.service;

import com.Project.UPIRecon.recon.dto.NormalizedTransactionDTO;
import com.Project.UPIRecon.recon.entity.NormalizedTransaction;
import com.Project.UPIRecon.recon.repository.NormalizedTransactionRepository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NormalizedTransactionService {

	@Autowired
	private NormalizedTransactionRepository repository;

	public void saveNormalizedTransaction(NormalizedTransactionDTO dto) {
		NormalizedTransaction entity = new NormalizedTransaction();
		entity.setTransactionId(dto.getTransactionId());
		entity.setSender(dto.getSenderUpi());
		entity.setReceiver(dto.getReceiverUpi());
		entity.setAmount(dto.getAmount());
		entity.setTimestamp(dto.getTimestamp());
		repository.save(entity);
	}

	public List<NormalizedTransactionDTO> getAllNormalizedTransactions() {
		List<NormalizedTransaction> transactions = repository.findAll();
		System.out.println("Normalized Transactions Fetched: " + transactions.size());
		transactions.forEach(System.out::println);


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
