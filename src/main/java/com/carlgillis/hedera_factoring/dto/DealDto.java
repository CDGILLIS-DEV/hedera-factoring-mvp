package com.carlgillis.hedera_factoring.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DealDto {
    @NotNull(message = "Invoice ID is required")
    private Long invoiceId;

    @NotBlank(message = "Purchaser account ID is required")
    private String purchaserAccountId;

    @NotNull(message = "Purchase price is required")
    @DecimalMin(value = "0.01", message = "Purchase price must be greater than zero")
    private BigDecimal purchasePrice;

    @NotNull(message = "Status is required")
    private String status;


    private Instant createdAt;

    @NotNull(message = "Transaction ID is required")
    private String transactionId;
}
