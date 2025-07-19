package com.Project.UPIRecon.transact.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TransactionEvent {

	@NotBlank(message = "Transaction ID is required")
	private String transactionId;
	@NotNull(message = "Amount is required")
	private BigDecimal amount;
	@NotBlank(message = "Sender is required")
	private String sender;
	@NotBlank(message = "Receiver is required")
	private String receiver;
	private String status;
	@NotBlank(message = "TimeStamp is required")
	private LocalDateTime timestamp;

	// ✅ Constructor
	public TransactionEvent(String transactionId, String sender, String receiver, BigDecimal amount,
			LocalDateTime timestamp, String status) {
		this.transactionId = transactionId;
		this.sender = sender;
		this.receiver = receiver;
		this.amount = amount;
		this.status = status;
		this.timestamp = timestamp;
	}

	// ✅ No-arg constructor (required for deserialization)
	public TransactionEvent() {
	}

	// ✅ Getters and Setters
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
}
