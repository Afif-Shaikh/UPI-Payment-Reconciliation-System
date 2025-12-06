package com.Project.UPIRecon.report.repository;

import com.Project.UPIRecon.report.entity.ReconciliationResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface ReconciliationResultRepository extends JpaRepository<ReconciliationResult, UUID> {

    List<ReconciliationResult> findByTransactionTimeBetween(LocalDateTime start, LocalDateTime end);

	List<ReconciliationResult> findByStatus(String status);

	ReconciliationResult findByNormalizedKeyAndStatus(String normalizedKey, String status);
}
