package com.Project.UPIRecon.report.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
@Table(name = "reconciliation_results")
public class ReconciliationResult {

    @Id
    @GeneratedValue(generator = "UUID")
//    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
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
