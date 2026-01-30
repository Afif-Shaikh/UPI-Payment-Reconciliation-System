package com.Project.UPIRecon.normalizer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionDTO {

    @NotBlank(message = "Transaction ID is required")
    private String transactionId;

    @NotNull(message = "Amount is required")
    private BigDecimal amount;

    @NotNull(message = "Timestamp is required")
    private LocalDateTime timestamp;

    @NotBlank(message = "Sender is required")
    private String sender;

    @NotBlank(message = "Receiver is required")
    private String receiver;
}
