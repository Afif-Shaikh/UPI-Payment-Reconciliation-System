package com.Project.UPIRecon.recon.repository;

import com.Project.UPIRecon.recon.entity.NormalizedTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NormalizedTransactionRepository extends JpaRepository<NormalizedTransaction, String> {
}
