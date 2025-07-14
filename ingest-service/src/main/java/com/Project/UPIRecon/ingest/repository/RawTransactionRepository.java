package com.Project.UPIRecon.ingest.repository;

import com.Project.UPIRecon.ingest.model.RawTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RawTransactionRepository extends JpaRepository<RawTransaction, Long> {
}
