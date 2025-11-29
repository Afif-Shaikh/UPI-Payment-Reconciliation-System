package com.Project.UPIRecon.ingest.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.validation.constraints.*;

@Entity
@Data
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
}
