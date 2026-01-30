package com.Project.UPIRecon.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReconciliationResultEvent {
    private String id;
    private String normalizedKey;
    private BigDecimal amount;
    private String senderUpi;
    private String receiverUpi;
    private LocalDateTime transactionTime;
    private String status;
    private String remarks;
    private int transactionCount;
}
