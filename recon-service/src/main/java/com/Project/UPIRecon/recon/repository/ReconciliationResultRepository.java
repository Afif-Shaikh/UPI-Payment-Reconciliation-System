package com.Project.UPIRecon.recon.repository;

import com.Project.UPIRecon.recon.entity.ReconciliationResult;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReconciliationResultRepository extends JpaRepository<ReconciliationResult, String> {
    // Custom queries (if needed) can be added here later
	List<ReconciliationResult> findByTransactionTimeBetween(LocalDateTime start, LocalDateTime end);
}
