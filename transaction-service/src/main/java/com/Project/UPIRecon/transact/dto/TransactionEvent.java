package com.Project.UPIRecon.transact.dto;

import java.math.BigDecimal;
import lombok.*;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransactionEvent {

	@NotBlank(message = "Transaction ID is required")
	private String transactionId;
	@NotBlank(message = "Sender is required")
	private String sender;
	@NotBlank(message = "Receiver is required")
	private String receiver;
	@NotNull(message = "Amount is required")
	private BigDecimal amount;
	@NotNull(message = "TimeStamp is required")
	private LocalDateTime timestamp;
	private String status;
}
