package com.carlgillis.hedera_factoring.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceResponseDto {
    private Long id;
    private Long customerId;
    private BigDecimal amount;
    private String currency;
    private Instant dueDate;
    private String status;
}
