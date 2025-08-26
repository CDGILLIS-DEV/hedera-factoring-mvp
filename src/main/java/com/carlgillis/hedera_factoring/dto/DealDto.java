package com.carlgillis.hedera_factoring.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class DealDto {
    @NotNull
    private Long invoiceId;

    @NotBlank
    private String purchaserAccountId;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal purchasePrice;
}
