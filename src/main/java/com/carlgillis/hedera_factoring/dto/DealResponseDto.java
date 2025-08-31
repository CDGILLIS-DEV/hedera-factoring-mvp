package com.carlgillis.hedera_factoring.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DealResponseDto {
    private Long id;
    private Long invoiceId;
    private String purchaserAccountId;
    private BigDecimal purchasePrice;
    private String status;
    private Instant createdAt;
}
