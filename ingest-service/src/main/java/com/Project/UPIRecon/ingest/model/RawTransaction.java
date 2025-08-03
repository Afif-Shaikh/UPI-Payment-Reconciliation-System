package com.Project.UPIRecon.ingest.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "raw_transactions")
public class RawTransaction {

	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String transactionId;
	
    @NotBlank(message = "Sender UPI is required")
    private String senderUpi;

    @NotBlank(message = "Receiver UPI is required")
    private String receiverUpi;

	@NotNull(message = "Amount is required")
	@DecimalMin(value = "0.01", message = "Amount must be positive")
	@DecimalMax(value = "100000.00", message = "UPI transaction amount cannot exceed ₹1,00,000")
	private BigDecimal amount;

	@NotBlank(message = "Status is required")
	private String status;

	@NotBlank(message = "Source is required")
	private String source; // e.g., bank, psp, merchant, npci

	@Column(columnDefinition = "TEXT")
	private String remarks;

    private LocalDateTime timeStamp ;
//    		=LocalDateTime.now();

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }
}
