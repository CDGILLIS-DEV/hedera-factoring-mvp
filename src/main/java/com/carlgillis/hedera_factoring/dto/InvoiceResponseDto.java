package com.carlgillis.hedera_factoring.dto;

import lombok.*;
import org.springframework.web.bind.annotation.BindParam;

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
}
