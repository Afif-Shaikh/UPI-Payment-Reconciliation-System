package com.Project.UPIRecon.ingest.service;

import com.Project.UPIRecon.ingest.model.RawTransaction;
import com.Project.UPIRecon.ingest.repository.RawTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RawTransactionService {

    @Autowired
    private RawTransactionRepository repository;

    public void saveAll(List<RawTransaction> transactions) {
        repository.saveAll(transactions);
    }
}
