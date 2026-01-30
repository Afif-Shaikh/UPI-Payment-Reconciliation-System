package com.Project.UPIRecon.normalizer.dto;

import java.math.BigDecimal;
import lombok.*;
import java.time.LocalDateTime;

@Data
public class NormalizedTransactionDTO {

    private String transactionId;
    private String senderUpi;
    private String receiverUpi;
    private BigDecimal amount;
    private LocalDateTime timestamp;
}
