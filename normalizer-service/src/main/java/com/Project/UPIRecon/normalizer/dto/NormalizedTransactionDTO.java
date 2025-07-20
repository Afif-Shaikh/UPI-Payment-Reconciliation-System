package com.Project.UPIRecon.normalizer.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class NormalizedTransactionDTO {

    private String transactionId;
    private String senderUpi;
    private String receiverUpi;
    private BigDecimal amount;
    private LocalDateTime timestamp;

    // Constructors
    public NormalizedTransactionDTO() {}

    public NormalizedTransactionDTO(String transactionId, String senderUpi, String receiverUpi, BigDecimal amount, LocalDateTime timestamp) {
        this.transactionId = transactionId;
        this.senderUpi = senderUpi;
        this.receiverUpi = receiverUpi;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    // Getters and Setters

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
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
//        return "NormalizedTransactionDTO{" +
//                "transactionId='" + transactionId + '\'' +
//                ", senderUpi='" + senderUpi + '\'' +
//                ", receiverUpi='" + receiverUpi + '\'' +
//                ", amount=" + amount +
//                ", timestamp=" + timestamp +
//                '}';
//    }
}
