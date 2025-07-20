package com.Project.UPIRecon.recon.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "reconciliation_results")
public class ReconciliationResult {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

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

    // Getters & Setters
    
    public int getTransactionCount() {
        return transactionCount;
    }

    public void setTransactionCount(int transactionCount) {
        this.transactionCount = transactionCount;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNormalizedKey() {
        return normalizedKey;
    }

    public void setNormalizedKey(String normalizedKey) {
        this.normalizedKey = normalizedKey;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getSenderUpi() {
        return senderUpi;
    }

    public void setSenderUpi(String senderUpi) {
        this.senderUpi = senderUpi;
    }

    public String getReceiverUpi() {
        return receiverUpi;
    }

    public void setReceiverUpi(String receiverUpi) {
        this.receiverUpi = receiverUpi;
    }

    public LocalDateTime getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(LocalDateTime transactionTime) {
        this.transactionTime = transactionTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}

