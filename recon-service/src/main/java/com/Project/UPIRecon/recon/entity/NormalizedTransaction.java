package com.Project.UPIRecon.recon.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class NormalizedTransaction {

    @Id
    private String transactionId;
    private String sender;
    private String receiver;
    private BigDecimal amount;
    private LocalDateTime timestamp;

    public NormalizedTransaction() {
        // No-args constructor
    }

    public NormalizedTransaction(String transactionId, String sender, String receiver, BigDecimal amount, LocalDateTime timestamp) {
        this.transactionId = transactionId;
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

//    @Override
//    public String toString() {
//        return "NormalizedTransaction{" +
//                "transactionId='" + transactionId + '\'' +
//                ", sender='" + sender + '\'' +
//                ", receiver='" + receiver + '\'' +
//                ", amount=" + amount +
//                ", timestamp=" + timestamp +
//                '}';
//    }
}
