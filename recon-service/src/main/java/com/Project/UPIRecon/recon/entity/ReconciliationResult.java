package com.Project.UPIRecon.recon.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.*;

@Entity
@Data
@Table(name = "reconciliation_results")
public class ReconciliationResult {

    @Id
//    @GeneratedValue(generator = "UUID")
//    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @Column(nullable = false)
    private String normalizedKey;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String senderUpi;

    @Column(nullable = false)
    private String receiverUpi;

    @Column(nullable = false)
    private LocalDateTime transactionTime;

    @Column(nullable = false)
    private String status; // MATCHED / MISSING / MISMATCH

    private String remarks; // Optional field for extra info
    
    @Column(nullable = false)
    private int transactionCount;

}

