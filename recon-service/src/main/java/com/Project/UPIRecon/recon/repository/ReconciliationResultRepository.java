package com.Project.UPIRecon.recon.repository;

import com.Project.UPIRecon.recon.entity.ReconciliationResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReconciliationResultRepository extends JpaRepository<ReconciliationResult, Long> {
    // Custom queries (if needed) can be added here later
}
