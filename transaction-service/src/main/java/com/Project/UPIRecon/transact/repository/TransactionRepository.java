package com.Project.UPIRecon.transact.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Project.UPIRecon.transact.entity.Transaction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
    // You can add custom queries here if needed
	Page<Transaction> findBySenderOrReceiver(String sender, String receiver, Pageable pageable);
}

