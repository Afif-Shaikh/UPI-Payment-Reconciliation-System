package com.Project.UPIRecon.recon.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class NormalizedTransactionDTO {

    private String transactionId;
    private BigDecimal amount;
    private String senderUpi;
    private String receiverUpi;
    private LocalDateTime timestamp;

    // Getters and Setters

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
