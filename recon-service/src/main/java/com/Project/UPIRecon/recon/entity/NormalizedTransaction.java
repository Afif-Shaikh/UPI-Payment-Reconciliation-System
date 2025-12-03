package com.Project.UPIRecon.recon.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "normalized_upi_transactions")
public class NormalizedTransaction {

	@Id
	private String transactionId;
	@Column(name = "sender_upi", nullable = false)
	private String sender;
	@Column(name = "receiver_upi", nullable = false)
	private String receiver;
	@Column(nullable = false)
	private BigDecimal amount;
	private LocalDateTime timestamp;
	private String normalizedKey;
	
//    @Override
//    public String toString() {
//        return "NormalizedTransaction{" +
//                "transactionId='" + transactionId + '\'' +
//                ", sender='" + sender + '\'' +
//                ", receiver='" + receiver + '\'' +
//                ", amount=" + amount +
//                ", timestamp=" + timestamp +
//                '}';
//    }
}
