package com.Project.UPIRecon.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class NormalizedTransactionEvent {
    private String transactionId;
    private String normalizedKey;
    private String senderUpi;
    private String receiverUpi;
    private BigDecimal amount;
    private LocalDateTime timestamp;
    private String source;   
}
