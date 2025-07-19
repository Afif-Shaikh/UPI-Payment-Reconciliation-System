package com.Project.UPIRecon.normalizer.repository;

import com.Project.UPIRecon.normalizer.entity.NormalizedTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NormalizedTransactionRepository extends JpaRepository<NormalizedTransaction, String> {
}
