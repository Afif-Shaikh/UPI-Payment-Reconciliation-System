package com.Project.UPIRecon.transact.entity;

import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "transactions")
public class Transaction {

    @Id
    @Column(name = "transaction_id", nullable = false, unique = true)
    private String transactionId;

    @NotNull(message = "Amount is required")
    private BigDecimal amount;

    @NotNull(message = "TimeStamp is required")
    private LocalDateTime timestamp;

    @NotNull(message = "Sender is required")
    private String sender;

    @NotNull(message = "Receiver is required")
    private String receiver;
}
