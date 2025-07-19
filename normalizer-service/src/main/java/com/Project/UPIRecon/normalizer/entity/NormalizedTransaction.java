package com.Project.UPIRecon.normalizer.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "normalized_upi_transactions")
public class NormalizedTransaction {

    @Id
    @NotNull(message = "TrxnId is required")
    private String transactionId;
    @NotNull(message = "Amount is required")
    private BigDecimal amount;
    @NotNull(message = "Sender is required")
    private String senderUpi;
    @NotNull(message = "Receiver is required")
    private String receiverUpi;
    @NotNull(message = "TimeStamp is required")
    private LocalDateTime timestamp;
	private LocalDateTime transactionTime;

    // Getters and Setters
	
	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

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

    public LocalDateTime getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(LocalDateTime transactionTime) {
        this.transactionTime = transactionTime;
    }
}
