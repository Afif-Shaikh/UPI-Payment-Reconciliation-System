package com.Project.UPIRecon.ingest.service;

import com.Project.UPIRecon.ingest.model.RawTransaction;
import com.Project.UPIRecon.ingest.repository.RawTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Project.UPIRecon.config.LoggingConfig;
import org.slf4j.Logger;
import java.util.List;

@Service
public class RawTransactionService {

    @Autowired
    private RawTransactionRepository repository;
    
    private static final Logger log = LoggingConfig.getLogger(RawTransactionService.class);

    public void saveAll(List<RawTransaction> transactions) {
    	
    	 log.info("Saving {} raw transactions to the database...", transactions.size());
    	 
        try {
			repository.saveAll(transactions);
			log.info("Successfully saved {} raw transactions.", transactions.size());
		} catch (Exception e) {
			log.error("Failed to save transactions. Error: {}", e.getMessage(), e);
            throw e;
		}
    }
}
