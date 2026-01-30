package com.Project.UPIRecon.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class RawTransactionEvent {
    private String transactionId;
    private String senderUpi;
    private String receiverUpi;
    private BigDecimal amount;
    private LocalDateTime timestamp;
    private String status;
    private String source;
    private String remarks;
}
