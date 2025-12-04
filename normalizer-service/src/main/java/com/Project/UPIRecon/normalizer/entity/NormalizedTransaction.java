package com.Project.UPIRecon.normalizer.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.Project.UPIRecon.dto.NormalizedTransactionEvent;

@Entity
@Data
@NoArgsConstructor
@Table(name = "normalized_upi_transactions")
public class NormalizedTransaction {

	@Id
    @NotNull(message = "TrxnId is required")
    private String transactionId;
    @NotNull(message = "Amount is required")
    private BigDecimal amount;
    @NotNull(message = "Sender is required")
    private String senderUpi;
    @NotNull(message = "Receiver is required")
    private String receiverUpi;
    @NotNull(message = "TimeStamp is required")
    private LocalDateTime timestamp;
    @NotNull
    private String source; 
    
    private String normalizedKey;
    
    public NormalizedTransaction(NormalizedTransactionEvent event) {
    	this.transactionId = event.getTransactionId();
        this.amount = event.getAmount();
        this.senderUpi = event.getSenderUpi();
        this.receiverUpi = event.getReceiverUpi();
        this.timestamp = event.getTimestamp();
        this.normalizedKey=event.getNormalizedKey();
        this.source = event.getSource();

	}
}
