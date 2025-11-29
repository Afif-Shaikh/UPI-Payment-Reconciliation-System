package com.Project.UPIRecon.recon.dto;

import java.math.BigDecimal;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
public class NormalizedTransactionDTO {

	private String transactionId;
	private BigDecimal amount;
	private String senderUpi;
	private String receiverUpi;
	private LocalDateTime timestamp;
}
