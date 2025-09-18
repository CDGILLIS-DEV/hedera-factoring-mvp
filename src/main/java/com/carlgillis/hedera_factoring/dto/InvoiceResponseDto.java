package com.carlgillis.hedera_factoring.dto;

import com.carlgillis.hedera_factoring.domain.InvoiceStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

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
    private LocalDate dueDate;
    private InvoiceStatus status;
}
