package com.Project.UPIRecon.normalizer.dto;

import java.math.BigDecimal;
import lombok.*;
import java.time.LocalDateTime;

@Data
public class TransactionKafkaEvent {
    private String transactionId;
    private BigDecimal amount;
    private String sender;
    private String receiver;
    private LocalDateTime timestamp;
}
